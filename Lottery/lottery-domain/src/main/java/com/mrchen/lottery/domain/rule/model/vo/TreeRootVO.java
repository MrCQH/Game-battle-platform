package com.mrchen.lottery.domain.rule.model.vo;

/**
 * @description: 决策树根配置
 * @author：cqh
 * @date: 2023/6/21
 */
public class TreeRootVO {
    private Long treeId;
    private Long treeRootId;
    private String treeName;

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Long getTreeRootId() {
        return treeRootId;
    }

    public void setTreeRootId(Long treeRootId) {
        this.treeRootId = treeRootId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    @Override
    public String toString() {
        return "TreeRootVO{" +
                "treeId=" + treeId +
                ", treeRootId=" + treeRootId +
                ", treeName='" + treeName + '\'' +
                '}';
    }
}
