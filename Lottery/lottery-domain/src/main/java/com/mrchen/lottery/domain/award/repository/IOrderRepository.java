package com.mrchen.lottery.domain.award.repository;

import com.mrchen.lottery.domain.award.model.aggregates.AwardInfoLimitPageRich;
import com.mrchen.lottery.domain.award.model.req.AwardInfoLimitPageReq;

/**
 * @description: 奖品表仓储服务接口
 * @author：cqh
 * @date: 2023/6/7
 */
public interface IOrderRepository {
    /**
     * 更新奖品发放状态
     *
     * @param uId               用户ID
     * @param orderId           订单ID
     * @param awardId           奖品ID
     * @param grantState        奖品状态
     */
    void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState);

    /**
     * 查询奖品信息
     * @param req
     * @return AwardInfoLimitPageRich
     */
    AwardInfoLimitPageRich queryAwardInfoLimitPage(AwardInfoLimitPageReq req);
}
