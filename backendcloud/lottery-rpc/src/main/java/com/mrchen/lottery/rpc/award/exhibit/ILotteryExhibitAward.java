package com.mrchen.lottery.rpc.award.exhibit;

import com.mrchen.lottery.rpc.award.exhibit.req.AwardPageReq;
import com.mrchen.lottery.rpc.award.exhibit.res.AwardRes;

/**
 * 抽奖活动奖品接口
 */
public interface ILotteryExhibitAward {
    /**
     * 通过分页查询奖品列表信息
     *
     * @param req   查询参数
     * @return      查询结果
     */
    AwardRes queryAwardListByPage(AwardPageReq req);
}
