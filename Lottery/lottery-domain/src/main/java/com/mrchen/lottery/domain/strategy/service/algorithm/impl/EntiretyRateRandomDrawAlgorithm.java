package com.mrchen.lottery.domain.strategy.service.algorithm.impl;

import com.mrchen.lottery.common.StrategyModeEnum;
import com.mrchen.lottery.domain.strategy.annotation.StrategyMode;
import com.mrchen.lottery.domain.strategy.model.vo.AwardRateVO;
import com.mrchen.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;



/**
 * 必中奖策略抽奖，排掉已经中奖的概率，重新计算中奖范围
 */
@Component("entiretyRateRandomDrawAlgorithm")
@StrategyMode(strategyMode = StrategyModeEnum.ENTIRETY_RATE_RANDOM_DRAW_ALGORITHM)
public class EntiretyRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        BigDecimal differenceDenominator = BigDecimal.ZERO;

        List<AwardRateVO> differenceAwardRateList = new ArrayList<>();
        List<AwardRateVO> awardRateIntervalValList = awardRateInfoMap.get(strategyId);
        for (AwardRateVO awardRateInfo : awardRateIntervalValList){
            String awardId = awardRateInfo.getAwardId();
            if (excludeAwardIds.contains(awardId)){
                continue;
            }
            // new_list_rate
            differenceAwardRateList.add(awardRateInfo);
            // denominator_sum
            differenceDenominator = differenceDenominator.add(awardRateInfo.getAwardRate());
        }

        if (differenceAwardRateList.size() == 0) return null;
        if (differenceAwardRateList.size() == 1)
            return differenceAwardRateList.get(0).getAwardId();

        SecureRandom secureRandom = new SecureRandom();
        int randomVal = secureRandom.nextInt(100) + 1;

        String awardId = null;
        int cursorVal = 0;
        for (AwardRateVO awardRateInfo : differenceAwardRateList){
            // 保留两位小数 0.52 -> 52
            int rateVal = awardRateInfo.getAwardRate()
                    .divide(differenceDenominator, 2, BigDecimal.ROUND_UP)
                    .multiply(new BigDecimal(100)).intValue();

            if (randomVal <= (cursorVal + rateVal)){
                awardId = awardRateInfo.getAwardId();
                break;
            }
            cursorVal += rateVal;
        }

        return awardId;
    }
}
