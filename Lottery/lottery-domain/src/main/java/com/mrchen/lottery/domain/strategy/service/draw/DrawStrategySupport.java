package com.mrchen.lottery.domain.strategy.service.draw;

import com.mrchen.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.mrchen.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.mrchen.lottery.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;

/**
 * @description: 抽奖策略数据支撑，一些通用的数据服务
 * @author：cqh
 * @date: 2023/5/20
 */
public class DrawStrategySupport extends DrawConfig{

    @Resource
    protected IStrategyRepository strategyRepository;

    /**
     *
     * @param strategyId 策略ID
     * @return 策略配置信息
     */
    protected StrategyRich queryStrategyRich(Long strategyId){
        return strategyRepository.queryStrategyRich(strategyId);
    }

    protected AwardBriefVO queryAwardInfoByAwardId(String awardId){
        return strategyRepository.queryAwardInfo(awardId);
    }
}
