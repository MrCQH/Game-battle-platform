package com.mrchen.lottery.application.process.draw.res;

import com.mrchen.lottery.common.Result;

/**
 * @description: 规则量化人群结果
 * @author：cqh
 * @date: 2023/6/21
 */
public class RuleQuantificationCrowdResult extends Result {
    private Long activityId;


    public RuleQuantificationCrowdResult(String code, String info) {
        super(code, info);
    }

    public Long getActivityId() {

        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
