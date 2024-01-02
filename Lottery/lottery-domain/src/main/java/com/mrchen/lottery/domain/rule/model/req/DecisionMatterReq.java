package com.mrchen.lottery.domain.rule.model.req;

import java.util.Map;

/**
 * @description: 决策物料
 * @author：cqh
 * @date: 2023/6/21
 */
public class DecisionMatterReq {
    /** 规则树ID */
    private Long treeId;
    /** 用户ID */
    private String userId;
    /** 决策内容的值Tag */
    private Map<String, Object> valMap;

    public DecisionMatterReq() {
    }

    public DecisionMatterReq(Long treeId, String userId, Map<String, Object> valMap) {
        this.treeId = treeId;
        this.userId = userId;
        this.valMap = valMap;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Object> getValMap() {
        return valMap;
    }

    public void setValMap(Map<String, Object> valMap) {
        this.valMap = valMap;
    }
}
