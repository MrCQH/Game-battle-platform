package com.mrchen.lottery.infrastructure.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.mrchen.lottery.domain.award.model.req.AwardInfoLimitPageReq;
import com.mrchen.lottery.infrastructure.po.UserStrategyExport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 用户策略计算结果表DAO
 * @author：cqh
 * @date: 2023/6/19
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserStrategyExportDao{
    /**
     * 新增数据
     * @param userStrategyExport 用户策略
     */
    @DBRouter(key = "uId")
    void insert(UserStrategyExport userStrategyExport);

    /**
     * 查询数据
     * @param uId 用户ID
     * @return 用户策略
     */
    @DBRouter
    UserStrategyExport queryUserStrategyExportByUId(String uId);

    /**
     * 更新发奖状态
     * @param userStrategyExport 发奖信息
     */
    @DBRouter
    void updateUserAwardState(UserStrategyExport userStrategyExport);

    /**
     * 更新发送MQ状态
     * @param userStrategyExport 发送消息
     */
    @DBRouter
    void updateInvoiceMqState(UserStrategyExport userStrategyExport);

    /**
     * 扫描发货单 MQ 状态，把未发送 MQ 的单子扫描出来，做补偿
     *
     * @return 发货单
     */
    List<UserStrategyExport> scanInvoiceMqState();

    /**
     * 查询奖品分页数据数量
     *
     * @param req 入参
     * @return    结果
     */
    @DBRouter
    Long queryAwardInfoCount(AwardInfoLimitPageReq req);

    /**
     * 查询奖品分页数据列表
     *
     * @param req   入参
     * @return      结果
     */
    @DBRouter
    List<UserStrategyExport> queryAwardInfoList(AwardInfoLimitPageReq req);
}
