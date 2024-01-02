package com.mrchen.lottery.domain.activity.model.req;

import java.util.Date;

/**
 * @description: 参与活动请求
 * @author：cqh
 * @date: 2023/6/19
 */
public class PartakeReq {
    private String uid;
    private Long activityId;
    /** 活动领取时间 */
    private Date partakeDate;

    public PartakeReq() {
    }

    public PartakeReq(String uid, Long activityId) {
        this.uid = uid;
        this.activityId = activityId;
        this.partakeDate = new Date();
    }

    public PartakeReq(String uid, Long activityId, Date partakeDate) {
        this.uid = uid;
        this.activityId = activityId;
        this.partakeDate = partakeDate;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Date getPartakeDate() {
        return partakeDate;
    }

    public void setPartakeDate(Date partakeDate) {
        this.partakeDate = partakeDate;
    }
}
