package com.mrchen.lottery.application.process.deploy;

import com.mrchen.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import com.mrchen.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;

/**
 * @description: 活动部署接口；查询列表、创建活动、修改活动、删除活动(一般实际场景为逻辑删除)，如果活动的部署需要做一些逻辑校验，那么可以在这一层做编排
 * @author: 小傅哥，微信：fustack
 * @date: 2021/12/11
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public interface IActivityDeployProcess {

    /**
     * 查询活动分页查询聚合对象
     *
     * @param req 请求参数；分页、活动
     * @return    查询结果
     */
    ActivityInfoLimitPageRich queryActivityInfoLimitPage(ActivityInfoLimitPageReq req);

}
