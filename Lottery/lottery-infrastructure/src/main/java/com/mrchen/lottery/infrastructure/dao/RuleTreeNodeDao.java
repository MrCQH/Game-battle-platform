package com.mrchen.lottery.infrastructure.dao;

import com.mrchen.lottery.infrastructure.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 规则树节点
 * @author：cqh
 * @date: 2023/6/21
 */
@Mapper
public interface RuleTreeNodeDao {
    /**
     * 查询决策树节点
     * @param treeId 规则树ID
     * @return 规则树节点集合
     */
    List<RuleTreeNode> queryRuleTreeNodeList(Long treeId);

    /**
     * 查询决策树节点数量
     * @param treeId 规则树ID
     * @return 节点数量
     */
    int queryTreeNodeCount(Long treeId);

    /**
     * 查询决策树节点
     * @param treeId    决策树
     * @return          节点集合
     */
    List<RuleTreeNode> queryTreeRulePoint(Long treeId);

}
