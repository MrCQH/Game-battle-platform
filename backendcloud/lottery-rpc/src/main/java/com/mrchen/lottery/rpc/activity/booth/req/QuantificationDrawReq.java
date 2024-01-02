package com.mrchen.lottery.rpc.activity.booth.req;

import java.util.Map;

/**
 * @description: 量化人群抽奖请求参数
 * @author：cqh
 * @date: 2023/6/21
 */
public class QuantificationDrawReq{
    private String uId;
    private Long treeId;
    private Map<String, Object> valMap;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Map<String, Object> getValMap() {
        return valMap;
    }

    public void setValMap(Map<String, Object> valMap) {
        this.valMap = valMap;
    }
}
