package com.mrchen.lottery.interfaces.facade;

import com.alibaba.fastjson.JSON;
import com.mrchen.lottery.common.Result;
import com.mrchen.lottery.domain.award.model.aggregates.AwardInfoLimitPageRich;
import com.mrchen.lottery.domain.award.model.req.AwardInfoLimitPageReq;
import com.mrchen.lottery.domain.award.service.exhibit.IAwardExhibit;
import com.mrchen.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.mrchen.lottery.interfaces.assembler.IMapping;
import com.mrchen.lottery.rpc.award.exhibit.ILotteryExhibitAward;
import com.mrchen.lottery.rpc.award.exhibit.dto.AwardDTO;
import com.mrchen.lottery.rpc.award.exhibit.req.AwardPageReq;
import com.mrchen.lottery.rpc.award.exhibit.res.AwardRes;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@DubboService(registry="zk-registry", version = "1.0.0", timeout = 5000)
@Component
public class LotteryExhibitAward implements ILotteryExhibitAward {
    private final Logger logger = LoggerFactory.getLogger(LotteryExhibitAward.class);

    @Resource
    private IAwardExhibit awardExhibit;

    @Resource
    private IMapping<DrawAwardVO, AwardDTO> awardExhibitMapping;


    @Override
    public AwardRes queryAwardListByPage(AwardPageReq req) {
        try {
            logger.info("活动部署分页数据查询开始: uId：{}", req.getuId());

            // 1. 包装入参
            AwardInfoLimitPageReq awardInfoLimitPageReq = new AwardInfoLimitPageReq(req.getPage(),req.getRows());
            awardInfoLimitPageReq.setuId(req.getuId());

            // 2. 查询结果
            AwardInfoLimitPageRich awardInfoLimitPageRich = awardExhibit.queryAwardInfoLimitPage(awardInfoLimitPageReq);
            Long count = awardInfoLimitPageRich.getCount();
            List<DrawAwardVO> drawAwardVOList = awardInfoLimitPageRich.getDrawAwardVOList();

            // 3. 转换对象
            List<AwardDTO> awardDTOList = awardExhibitMapping.sourceToTarget(drawAwardVOList);

            // 4. 封装数据
            AwardRes awardRes = new AwardRes(Result.buildSuccessResult());
            awardRes.setCount(count);
            awardRes.setAwardDTOList(awardDTOList);

            logger.info("奖励分页数据查询完成 uId：{} count：{}", req.getuId(), count);

            // 5. 返回结果
            return awardRes;
        } catch (Exception e) {
            logger.error("奖励分页数据查询失败 uId：{} reqStr：{}", req.getuId(), JSON.toJSON(req), e);
            return new AwardRes(Result.buildErrorResult());
        }
    }
}
