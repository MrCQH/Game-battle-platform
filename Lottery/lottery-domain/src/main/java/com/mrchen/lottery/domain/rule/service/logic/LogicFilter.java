package com.mrchen.lottery.domain.rule.service.logic;

import com.mrchen.lottery.domain.rule.model.req.DecisionMatterReq;
import com.mrchen.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * @description: 逻辑过滤引擎
 * @author：cqh
 * @date: 2023/6/21
 */
public interface LogicFilter {
    /**
     * 逻辑决策器
     * @param matterValue          决策值
     * @param treeNodeLineInfoList 决策链路信息
     * @return                     下一个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList);
    /**
     * 获取决策值
     *
     * @param decisionMatter 决策物料
     * @return               决策值
     */
    String matterValue(DecisionMatterReq decisionMatter);
}
