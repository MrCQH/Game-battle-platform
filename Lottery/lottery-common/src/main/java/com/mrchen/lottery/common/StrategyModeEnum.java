package com.mrchen.lottery.common;

/**
 * @description:
 * @author：cqh
 * @date: 2023/7/24
 */
public enum StrategyModeEnum{
    ENTIRETY_RATE_RANDOM_DRAW_ALGORITHM(1, "必中奖策略抽奖，排掉已经中奖的概率，重新计算中奖范围"),
    SINGLE_RATE_RANDOM_DRAW_ALGORITHM(2, "单项随机概率抽奖，抽到一个已经排掉的奖品则未中奖");

    private final Integer id;
    private final String description;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    StrategyModeEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
}
