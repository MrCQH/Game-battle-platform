package com.mrchen.lottery.application.process.draw.Impl;

import com.mrchen.lottery.application.mq.producer.KafkaProducer;
import com.mrchen.lottery.application.process.draw.IActivityProcess;
import com.mrchen.lottery.application.process.draw.req.DrawProcessReq;
import com.mrchen.lottery.application.process.draw.res.DrawProcessResult;
import com.mrchen.lottery.application.process.draw.res.RuleQuantificationCrowdResult;
import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.common.Result;
import com.mrchen.lottery.domain.activity.model.req.PartakeReq;
import com.mrchen.lottery.domain.activity.model.res.PartakeResult;
import com.mrchen.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import com.mrchen.lottery.domain.activity.model.vo.DrawOrderVO;
import com.mrchen.lottery.domain.activity.model.vo.InvoiceVO;
import com.mrchen.lottery.domain.activity.service.partake.IActivityPartake;
import com.mrchen.lottery.domain.rule.model.req.DecisionMatterReq;
import com.mrchen.lottery.domain.rule.model.res.EngineResult;
import com.mrchen.lottery.domain.rule.service.engine.EngineFilter;
import com.mrchen.lottery.domain.strategy.model.req.DrawReq;
import com.mrchen.lottery.domain.strategy.model.res.DrawResult;
import com.mrchen.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.mrchen.lottery.domain.strategy.service.draw.IDrawExec;
import com.mrchen.lottery.domain.support.ids.IIdGenerator;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 活动抽奖流程编排
 * @author：cqh
 * @date: 2023/6/20
 */
@Service
public class ActivityProcessImpl implements IActivityProcess {

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    private IActivityPartake activityPartake;

    @Resource(name = "ruleEngineHandle")
    private EngineFilter engineFilter;

    @Resource
    private IDrawExec drawExec;

    @Resource
    private KafkaProducer kafkaProducer;

    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {
        // 1. 领取活动
        PartakeResult partakeResult = activityPartake.doPartake(new PartakeReq(req.getuId(), req.getActivityId()));
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(partakeResult.getCode()) &&
                !Constants.ResponseCode.NOT_CONSUMED_TAKE.getCode().equals(partakeResult.getCode())){
            return new DrawProcessResult(partakeResult.getCode(), partakeResult.getCode());
        }

        // 2. 首次成功领取活动，发送 MQ 消息，削峰
        if (Constants.ResponseCode.SUCCESS.getCode().equals(partakeResult.getCode())) {
            ActivityPartakeRecordVO activityPartakeRecord = new ActivityPartakeRecordVO();
            activityPartakeRecord.setuId(req.getuId());
            activityPartakeRecord.setActivityId(req.getActivityId());
            activityPartakeRecord.setStockCount(partakeResult.getStockCount());
            activityPartakeRecord.setStockSurplusCount(partakeResult.getStockSurplusCount());
            // 发送 MQ 消息
            kafkaProducer.sendLotteryActivityPartakeRecord(activityPartakeRecord);
        }

        Long strategyId = partakeResult.getStrategyId();
        Long takeId = partakeResult.getTakeId();

        // 3. 执行抽奖
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq(req.getuId(), strategyId, String.valueOf(takeId)));
        if (Constants.DrawState.FAIL.getCode().equals(drawResult.getDrawState())){
            return new DrawProcessResult(Constants.ResponseCode.LOSING_DRAW.getCode(), Constants.ResponseCode.LOSING_DRAW.getInfo());
        }
        DrawAwardVO drawAwardInfo = drawResult.getDrawAwardInfo();

        // 4. 结果落库
        DrawOrderVO drawOrderVO = buildDrawOrderVO(req, strategyId, takeId, drawAwardInfo);
        Result recordResult = activityPartake.recordDrawOrder(drawOrderVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(recordResult.getCode())){
            return new DrawProcessResult(recordResult.getCode(), recordResult.getInfo());
        }

        // 5. 发送MQ, 触发发奖流程
        InvoiceVO invoiceVO = buildInvoiceVO(drawOrderVO);
        ListenableFuture<SendResult<String, Object>> future = kafkaProducer.sendLotteryInvoice(invoiceVO);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                // 5.1 MQ 消息发送完成，更新数据库表 user_strategy_export.mq_state = 1
                activityPartake.updateInvoiceMqState(invoiceVO.getuId(), invoiceVO.getOrderId(), Constants.MQState.COMPLETE.getCode());
            }
            @Override
            public void onFailure(Throwable throwable) {
                // 5.2 MQ 消息发送失败，更新数据库表 user_strategy_export.mq_state = 2 【等待定时任务扫码补偿MQ消息】
                activityPartake.updateInvoiceMqState(invoiceVO.getuId(), invoiceVO.getOrderId(), Constants.MQState.FAIL.getCode());
            }
        });


        // 6. 返回结果
        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardInfo);
    }

    @Override
    public RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req) {
        // 1. 量化决策
        EngineResult engineResult = engineFilter.process(req);
        if (!engineResult.isSuccess()){
            return new RuleQuantificationCrowdResult(Constants.ResponseCode.RULE_ERR.getCode(), Constants.ResponseCode.RULE_ERR.getInfo());
        }

        // 2. 封装结果
        RuleQuantificationCrowdResult ruleQuantificationCrowdResult =
                new RuleQuantificationCrowdResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        ruleQuantificationCrowdResult.setActivityId(Long.valueOf(engineResult.getNodeValue()));

        return ruleQuantificationCrowdResult;
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardVO drawAwardInfo){
        long orderId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setuId(req.getuId());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawAwardInfo.getStrategyMode());
        drawOrderVO.setGrantDate(drawAwardInfo.getGrantDate());
        drawOrderVO.setGrantState(Constants.GrantState.INIT.getCode());
        drawOrderVO.setGrantType(drawAwardInfo.getGrantType());
        drawOrderVO.setAwardId(drawAwardInfo.getAwardId());
        drawOrderVO.setAwardContent(drawAwardInfo.getAwardContent());
        drawOrderVO.setAwardName(drawAwardInfo.getAwardName());
        drawOrderVO.setAwardType(drawAwardInfo.getAwardType());
        return drawOrderVO;
    }

    private InvoiceVO buildInvoiceVO(DrawOrderVO drawOrderVO) {
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setuId(drawOrderVO.getuId());
        invoiceVO.setOrderId(drawOrderVO.getOrderId());
        invoiceVO.setAwardId(drawOrderVO.getAwardId());
        invoiceVO.setAwardType(drawOrderVO.getAwardType());
        invoiceVO.setAwardName(drawOrderVO.getAwardName());
        invoiceVO.setAwardContent(drawOrderVO.getAwardContent());
        invoiceVO.setShippingAddress(null);
        invoiceVO.setExtInfo(null);
        return invoiceVO;
    }
}
