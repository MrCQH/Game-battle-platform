package com.mrchen.lottery.domain.activity.model.vo;

/**
 * @description: 用户活动领取记录
 * @author：cqh
 * @date: 2023/6/20
 */
public class UserTakeActivityVO {
    private Long activityId;
    private Long takeId;
    private Long strategyId;
    /**
     * 活动单使用状态 0未使用 1已使用
     * Constants.TakeState
     */
    private Integer state;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getTakeId() {
        return takeId;
    }

    public void setTakeId(Long takeId) {
        this.takeId = takeId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserTakeActivityVO{" +
                "activityId=" + activityId +
                ", takeId=" + takeId +
                ", strategyId=" + strategyId +
                ", state=" + state +
                '}';
    }
}
