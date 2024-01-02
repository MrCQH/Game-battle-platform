package com.mrchen.lottery.domain.strategy.service.algorithm;

import com.mrchen.lottery.domain.strategy.model.vo.AwardRateVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共用的算法逻辑
 */
public abstract class BaseAlgorithm implements IDrawAlgorithm{
    private final int HASH_INCREMENT = 0x61c88647;

    private final int RETE_TUPLE_LENGTH = 128;

    // 存放概率与奖品对应的散列结果，strategyId -> rateTuple
    protected Map<Long, String[]> rateTupleMap = new ConcurrentHashMap<>();

    // 奖品区间概率值，strategyId -> [awardId->begin、awardId->end]
    protected Map<Long, List<AwardRateVO>> awardRateInfoMap = new ConcurrentHashMap<>();

    @Override
    public synchronized void initRateTuple(Long strategyId, List<AwardRateVO> awardRateInfoList){
        // 将信息缓存到本地，缓存优化总体概率，因为总体概率是固定的，而遇到单体概率，不必存入缓存，因为这一部分的抽奖算法是需要实时计算的
        awardRateInfoMap.put(strategyId, awardRateInfoList);

        String[] rateTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new String[RETE_TUPLE_LENGTH]);

        int cursorVal = 0;

        for (AwardRateVO awardRateInfo : awardRateInfoList){
            int rateVal = awardRateInfo.getAwardRate().multiply(new BigDecimal(100)).intValue();

             for (int i = cursorVal + 1; i <= (rateVal + cursorVal); i ++){
                rateTuple[hashIdx(i)] = awardRateInfo.getAwardId();
            }

            cursorVal += rateVal;
        }
    }

    @Override
    public boolean isExistRateTuple(Long strategyId) {
        return rateTupleMap.containsKey(strategyId);
    }

    protected int hashIdx(int val){
        int hashCode = val * HASH_INCREMENT + HASH_INCREMENT; //HASH_INCREMENT = 1640531527
        return hashCode & (RETE_TUPLE_LENGTH - 1); // 2 ^ 7 - 1 = 128 - 1
    }
}
