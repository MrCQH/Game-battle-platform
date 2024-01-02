package com.mrchen.lottery.domain.activity.service.stateflow;

import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.common.Result;

/**
 * @description: 状态处理接口
 * @author：cqh
 * @date: 2023/6/14
 */
public interface IStateHandler {
    /**
     * 提审
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result arraignment(Long activityId, Enum<Constants.ActivityState> currentStatus);
    /**
     * 审核通过
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result checkPass(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 审核拒绝
     *
     * @param activityId    活动id
     * @param currentStatus 当前状态
     * @return {@link Result}
     */
    Result checkRefuse(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 撤销审核
     *
     * @param activityId    活动id
     * @param currentStatus 当前状态
     * @return {@link Result}
     */
    Result checkRevoke(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 关闭
     *
     * @param activityId    活动id
     * @param currentStatus 当前状态
     * @return {@link Result}
     */
    Result close(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 开启
     *
     * @param activityId    活动id
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result open(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 运行活动中
     *
     * @param activityId    活动id
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result doing(Long activityId, Enum<Constants.ActivityState> currentStatus);
}
