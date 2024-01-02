package com.mrchen.lottery.domain.activity.service.partake;

import com.mrchen.lottery.domain.activity.model.req.PartakeReq;
import com.mrchen.lottery.domain.activity.model.vo.ActivityBillVO;
import com.mrchen.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * @description: 活动领取模操作，一些通用的数据服务
 * @author：cqh
 * @date: 2023/6/19
 */
public class ActivityPartakeSupport {
    @Resource
    protected IActivityRepository activityRepository;

    protected ActivityBillVO queryActivityBill(PartakeReq req){
        return activityRepository.queryActivityBill(req);
    }
}
