package com.mrchen.lottery.domain.rule.model.aggregate;

import com.mrchen.lottery.domain.rule.model.vo.TreeNodeVO;
import com.mrchen.lottery.domain.rule.model.vo.TreeRootVO;

import java.util.Map;

/**
 * @description: 规则树聚合对象
 * @author：cqh
 * @date: 2023/6/21
 */
public class TreeRuleRich {
    // 树根信息
    private TreeRootVO treeRoot;
    // TODO 决策树的数据构成部分
    // 每个树节点Id -> 子节点的信息，包括该节点与子节点连的边的信息
    // 节点中的值(标签中的信息，man、woman)、类型(是否是叶子节点)
    // 边的值(标签中的value，男、女、25岁)、类型(范围，大于小于)
    private Map<Long, TreeNodeVO> treeNodeMap;

    public TreeRootVO getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(TreeRootVO treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Map<Long, TreeNodeVO> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<Long, TreeNodeVO> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }
}
