package com.mrchen.lottery.domain.strategy.repository;

import com.mrchen.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.mrchen.lottery.domain.strategy.model.vo.AwardBriefVO;

import java.util.List;

public interface IStrategyRepository {
    StrategyRich queryStrategyRich(Long strategyId);
    AwardBriefVO queryAwardInfo(String awardId);
    /**
     * 查询无库存策略奖品ID
     * @param strategyId 策略ID
     * @return           返回结果
     */
    List<String> queryNoStockStrategyAwardList(Long strategyId);
    /**
     *
     * @param strategyId    策略ID
     * @param awardId       奖品ID
     * @return              扣减结果
     */
    boolean deductStock(Long strategyId, String awardId);
}
