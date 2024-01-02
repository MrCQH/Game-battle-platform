package com.kob.backend.service.impl.user.account;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.common.EasyResult;
import com.kob.backend.model.user.account.AwardLimitPageReq;
import com.kob.backend.service.user.account.IGetAwardService;
import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.rpc.award.exhibit.ILotteryExhibitAward;
import com.mrchen.lottery.rpc.award.exhibit.req.AwardPageReq;
import com.mrchen.lottery.rpc.award.exhibit.res.AwardRes;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Rpc 调用 lottery 服务
 */
@Service
public class GetAwardService implements IGetAwardService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @DubboReference(registry="zk-registry", version = "1.0.0", timeout = 5000)
    private ILotteryExhibitAward lotteryExhibitAward;


    @Override
    public EasyResult getAwardListLimitPage(AwardLimitPageReq req) {
        AwardPageReq awardPageReq = new AwardPageReq();
        awardPageReq.setPage(req.getPage(), req.getRows());
        awardPageReq.setuId(req.getUid());
        AwardRes awardRes = lotteryExhibitAward.queryAwardListByPage(awardPageReq);
        if (Constants.ResponseCode.SUCCESS.getCode().equals(awardRes.getResult().getCode())){
            logger.info("查询成功: {}", JSONObject.toJSONString(awardRes));
            return EasyResult.buildEasyResultSuccess(awardRes.getCount(), awardRes.getAwardDTOList());
        } else {
            logger.info("查询失败: {}", JSONObject.toJSONString(awardRes));
            return EasyResult.buildEasyResultError(awardRes.getResult().getCode());
        }
    }
}
