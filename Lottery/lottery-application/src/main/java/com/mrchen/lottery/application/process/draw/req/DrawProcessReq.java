package com.mrchen.lottery.application.process.draw.req;

/**
 * @description: 抽奖请求
 * @author：cqh
 * @date: 2023/6/20
 */
public class DrawProcessReq {
    private String uId;
    private Long activityId;

    public DrawProcessReq() {
    }

    public DrawProcessReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
