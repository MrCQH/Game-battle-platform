package com.mrchen.lottery.domain.rule.model.vo;

import java.util.List;

/**
 * @description: 决策树节点信息
 * @author：cqh
 * @date: 2023/6/21
 */
public class TreeNodeVO {
    private Long treeId;
    // 决策树节点ID
    private Long treeNodeId;
    // 节点类型：1. 中间节点  2. 叶子节点
    private Integer nodeType;
    // LeafNode的值
    private String nodeValue;
    // 规则key
    private String ruleKey;
    private String ruleDesc;
    // 节点链路(信息),这个节点每条边的信息
    private List<TreeNodeLineVO> treeNodeLineInfoList;

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Long getTreeNodeId() {
        return treeNodeId;
    }

    public void setTreeNodeId(Long treeNodeId) {
        this.treeNodeId = treeNodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public List<TreeNodeLineVO> getTreeNodeLineInfoList() {
        return treeNodeLineInfoList;
    }

    public void setTreeNodeLineInfoList(List<TreeNodeLineVO> treeNodeLineInfoList) {
        this.treeNodeLineInfoList = treeNodeLineInfoList;
    }
}
