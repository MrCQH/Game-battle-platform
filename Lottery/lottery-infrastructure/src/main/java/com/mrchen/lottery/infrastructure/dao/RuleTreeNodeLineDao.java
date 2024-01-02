package com.mrchen.lottery.infrastructure.dao;

import com.mrchen.lottery.infrastructure.po.RuleTree;
import com.mrchen.lottery.infrastructure.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 决策树节点的边DAO
 * @author：cqh
 * @date: 2023/6/21
 */
@Mapper
public interface RuleTreeNodeLineDao {
    /**
     * 查询规则树节点连线集合
     * @param req   入参
     * @return      规则树节点连线集合
     */
    List<RuleTreeNodeLine> queryRuleTreeNodeLineList(RuleTreeNodeLine req);

    /**
     * 查询规则树连线数量
     *
     * @param treeId    规则树ID
     * @return          规则树连线数量
     */
    int queryTreeNodeLineCount(Long treeId);
}
