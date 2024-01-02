package com.mrchen.lottery.infrastructure.po;

/**
 * @description: 决策树节点
 * @author：cqh
 * @date: 2023/6/21
 */
public class RuleTreeNode {
    private Long id;
    private Long treeId;
    // 节点类型 1. 叶节点  2. 叶子节点
    private Integer nodeType;
    // 若是叶子节点，它的值
    private String nodeValue;
    private String ruleKey;
    private String ruleDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
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
}
