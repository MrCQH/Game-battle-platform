package com.mrchen.lottery.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.mrchen.lottery.infrastructure.po.UserStrategyExport;
import com.mrchen.lottery.infrastructure.po.UserTakeActivityCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 用户活动参与次数表Dao
 * @author：cqh
 * @date: 2023/6/19
 */
@Mapper
public interface IUserTakeActivityCountDao {
    /**
     * 查询用户领取次数信息
     * @param userTakeActivityCountReq 请求入参【活动号、用户ID】
     * @return 领取结果
     */
    @DBRouter
    UserTakeActivityCount queryUserTakeActivityCount(UserTakeActivityCount userTakeActivityCountReq);

    /**
     * 插入领取次数信息
     * @param userTakeActivityCount 请求入参
     */
    // 已经手动开启路由，不需要分库分表
    void insert(UserTakeActivityCount userTakeActivityCount);

    /**
     * 更新领取次数信息
     * @param userTakeActivityCount 请求入参
     * @return 更新数量
     */
    // 已经手动开启路由，不需要分库分表
    int updateLeftCount(UserTakeActivityCount userTakeActivityCount);


}
