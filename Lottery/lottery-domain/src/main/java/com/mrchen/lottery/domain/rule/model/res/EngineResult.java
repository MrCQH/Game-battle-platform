package com.mrchen.lottery.domain.rule.model.res;

/**
 * @description: 决策结果
 * @author：cqh
 * @date: 2023/6/21
 */
public class EngineResult {
    // 执行结果
    private boolean isSuccess;

    private String userId;
    // 决策树ID
    private Long treeId;
    // 叶子节点ID
    private Long nodeId;
    // 叶子节点值
    private String nodeValue;

    public EngineResult() {
    }

    public EngineResult(String userId, Long treeId, Long nodeId, String nodeValue) {
        this.isSuccess = true;
        this.userId = userId;
        this.treeId = treeId;
        this.nodeId = nodeId;
        this.nodeValue = nodeValue;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }
}
