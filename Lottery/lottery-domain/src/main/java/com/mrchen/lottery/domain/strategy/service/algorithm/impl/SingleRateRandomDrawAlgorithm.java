package com.mrchen.lottery.domain.strategy.service.algorithm.impl;

import com.mrchen.lottery.common.StrategyModeEnum;
import com.mrchen.lottery.domain.strategy.annotation.StrategyMode;
import com.mrchen.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Component("singleRateRandomDrawAlgorithm")
@StrategyMode(strategyMode = StrategyModeEnum.SINGLE_RATE_RANDOM_DRAW_ALGORITHM)
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        assert rateTuple != null;

        SecureRandom secureRandom = new SecureRandom();
        int randomVal = secureRandom.nextInt(100) + 1;
        int idx = super.hashIdx(randomVal);

        String awardId = rateTuple[idx];
        if (excludeAwardIds.contains(awardId)) {
            return "未中奖";
        }

        return awardId;
    }
}
