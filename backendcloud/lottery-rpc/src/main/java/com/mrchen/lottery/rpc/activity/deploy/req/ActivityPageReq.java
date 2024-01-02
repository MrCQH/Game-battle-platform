package com.mrchen.lottery.rpc.activity.deploy.req;

import com.mrchen.lottery.common.PageRequest;

import java.io.Serializable;

/**
 * @description: 分页查询活动
 * @author: 小傅哥，微信：fustack
 * @date: 2021/12/11
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class ActivityPageReq extends PageRequest implements Serializable {

    /**
     * ERP ID，记录谁在操作
     */
    private String erpId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    public ActivityPageReq(int page, int rows) {
        super(page, rows);
    }

    public ActivityPageReq(String page, String rows) {
        super(page, rows);
    }

    public String getErpId() {
        return erpId;
    }

    public void setErpId(String erpId) {
        this.erpId = erpId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

}
