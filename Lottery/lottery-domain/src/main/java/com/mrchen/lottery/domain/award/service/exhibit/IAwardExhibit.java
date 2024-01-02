package com.mrchen.lottery.domain.award.service.exhibit;

import com.mrchen.lottery.domain.award.model.aggregates.AwardInfoLimitPageRich;
import com.mrchen.lottery.domain.award.model.req.AwardInfoLimitPageReq;

/**
 * @description: 展示个人奖励接口
 * @author：cqh
 */
public interface IAwardExhibit {
    /**
     * @description: 查询个人奖励
     * @param req
     * @return: com.mrchen.lottery.domain.award.model.aggregates.AwardInfoLimitPageRich
     */
    AwardInfoLimitPageRich queryAwardInfoLimitPage(AwardInfoLimitPageReq req);
}
