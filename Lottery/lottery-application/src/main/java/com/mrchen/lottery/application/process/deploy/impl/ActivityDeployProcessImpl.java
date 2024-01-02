package com.mrchen.lottery.application.process.deploy.impl;

import com.mrchen.lottery.application.process.deploy.IActivityDeployProcess;
import com.mrchen.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import com.mrchen.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import com.mrchen.lottery.domain.activity.service.deploy.IActivityDeploy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 活动部署实现
 * @author: 小傅哥，微信：fustack
 * @date: 2021/12/11
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Service
public class ActivityDeployProcessImpl implements IActivityDeployProcess {

    @Resource
    private IActivityDeploy activityDeploy;

    @Override
    public ActivityInfoLimitPageRich queryActivityInfoLimitPage(ActivityInfoLimitPageReq req) {
        return activityDeploy.queryActivityInfoLimitPage(req);
    }
}
