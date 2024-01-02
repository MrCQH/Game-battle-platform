package com.mrchen.lottery.infrastructure.dao;

import com.mrchen.lottery.infrastructure.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 决策树配置DAO
 * @author：cqh
 * @date: 2023/6/21
 */
@Mapper
public interface RuleTreeDao {
    /**
     * 规则树查询
     * @param treeId treeId
     * @return   规则树
     */
    RuleTree queryRuleTreeByTreeId(Long treeId);

    /**
     * 规则树简要信息查询
     * @param treeId 规则树ID
     * @return       规则树
     */
    RuleTree queryTreeSummaryInfo(Long treeId);
}
