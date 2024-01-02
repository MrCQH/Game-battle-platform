package com.mrchen.lottery.interfaces.facade;

import com.alibaba.fastjson.JSON;
import com.mrchen.lottery.application.process.deploy.IActivityDeployProcess;
import com.mrchen.lottery.common.Result;
import com.mrchen.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import com.mrchen.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import com.mrchen.lottery.domain.activity.model.vo.ActivityVO;
import com.mrchen.lottery.interfaces.assembler.IMapping;
import com.mrchen.lottery.rpc.activity.deploy.ILotteryActivityDeploy;
import com.mrchen.lottery.rpc.activity.deploy.dto.ActivityDTO;
import com.mrchen.lottery.rpc.activity.deploy.req.ActivityPageReq;
import com.mrchen.lottery.rpc.activity.deploy.res.ActivityRes;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author：cqh
 * @date: 2023/9/10
 */
@DubboService(registry="zk-registry")
@Component
public class LotteryActivityDeploy implements ILotteryActivityDeploy {
    private Logger logger = LoggerFactory.getLogger(LotteryActivityBooth.class);

    @Resource
    private IActivityDeployProcess activityDeploy;

    @Resource
    private IMapping<ActivityVO, ActivityDTO> activityMapping;

    @Override
    public ActivityRes queryActivityListByPageForErp(ActivityPageReq req) {
        try {
            logger.info("活动部署分页数据查询开始 erpID：{}", req.getErpId());

            // 1. 包装入参
            ActivityInfoLimitPageReq activityInfoLimitPageReq = new ActivityInfoLimitPageReq(req.getPage(),req.getRows());
            activityInfoLimitPageReq.setActivityId(req.getActivityId());
            activityInfoLimitPageReq.setActivityName(req.getActivityName());

            // 2. 查询结果
            ActivityInfoLimitPageRich activityInfoLimitPageRich = activityDeploy.queryActivityInfoLimitPage(activityInfoLimitPageReq);
            Long count = activityInfoLimitPageRich.getCount();
            List<ActivityVO> activityVOList = activityInfoLimitPageRich.getActivityVOList();

            // 3. 转换对象
            List<ActivityDTO> activityDTOList = activityMapping.sourceToTarget(activityVOList);

            // 4. 封装数据
            ActivityRes activityRes = new ActivityRes(Result.buildSuccessResult());
            activityRes.setCount(count);
            activityRes.setActivityDTOList(activityDTOList);

            logger.info("活动部署分页数据查询完成 erpID：{} count：{}", req.getErpId(), count);

            // 5. 返回结果
            return activityRes;
        } catch (Exception e) {
            logger.error("活动部署分页数据查询失败 erpID：{} reqStr：{}", req.getErpId(), JSON.toJSON(req), e);
            return new ActivityRes(Result.buildErrorResult());
        }
    }

}

