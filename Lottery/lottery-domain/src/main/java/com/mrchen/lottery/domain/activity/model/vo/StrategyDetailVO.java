package com.mrchen.lottery.domain.activity.model.vo;


import java.math.BigDecimal;

/**
 * @description: 策略详情配置
 * @author：cqh
 * @date: 2023/6/11
 */
public class StrategyDetailVO {
    private Long strategyId;
    private String awardId;
    private String awardName;
    // 奖品库存
    private Integer awardCount;
    // 奖品剩余库存
    private Integer awardSurplusCount;
    // 中奖概率
    private BigDecimal awardRate;

    public StrategyDetailVO() {
    }

    public StrategyDetailVO(Long strategyId, String awardId, String awardName,
                            Integer awardCount, Integer awardSurplusCount, BigDecimal awardRate) {
        this.strategyId = strategyId;
        this.awardId = awardId;
        this.awardName = awardName;
        this.awardCount = awardCount;
        this.awardSurplusCount = awardSurplusCount;
        this.awardRate = awardRate;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public Integer getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(Integer awardCount) {
        this.awardCount = awardCount;
    }

    public Integer getAwardSurplusCount() {
        return awardSurplusCount;
    }

    public void setAwardSurplusCount(Integer awardSurplusCount) {
        this.awardSurplusCount = awardSurplusCount;
    }

    public BigDecimal getAwardRate() {
        return awardRate;
    }

    public void setAwardRate(BigDecimal awardRate) {
        this.awardRate = awardRate;
    }

    @Override
    public String toString() {
        return "StrategyDetailVO{" +
                "strategyId=" + strategyId +
                ", awardId='" + awardId + '\'' +
                ", awardName='" + awardName + '\'' +
                ", awardCount=" + awardCount +
                ", awardSurplusCount=" + awardSurplusCount +
                ", awardRate=" + awardRate +
                '}';
    }
}
