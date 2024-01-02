package com.mrchen.lottery.domain.rule.repository;

import com.mrchen.lottery.domain.rule.model.aggregate.TreeRuleRich;

/**
 * @description: 规则信息仓储服务
 * @author：cqh
 * @date: 2023/6/21
 */
public interface IRuleRepository {
    /**
     * 查询规则决策树配置
     *
     * @param treeId    决策树ID
     * @return          决策树配置
     */
    TreeRuleRich queryRuleTreeRich(Long treeId);
}
