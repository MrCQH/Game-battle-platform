package com.mrchen.lottery.domain.activity.model.aggregates;

import com.mrchen.lottery.domain.activity.model.vo.ActivityVO;

import java.util.List;

/**
 * @description: 活动分页查询聚合对象
 * @author: 小傅哥，微信：fustack
 * @date: 2021/12/11
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class ActivityInfoLimitPageRich {

    private Long count;
    private List<ActivityVO> activityVOList;

    public ActivityInfoLimitPageRich() {
    }

    public ActivityInfoLimitPageRich(Long count, List<ActivityVO> activityVOList) {
        this.count = count;
        this.activityVOList = activityVOList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<ActivityVO> getActivityVOList() {
        return activityVOList;
    }

    public void setActivityVOList(List<ActivityVO> activityVOList) {
        this.activityVOList = activityVOList;
    }
}
