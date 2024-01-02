package com.mrchen.lottery.domain.rule.service.engine.Impl;

import com.mrchen.lottery.domain.rule.model.aggregate.TreeRuleRich;
import com.mrchen.lottery.domain.rule.model.req.DecisionMatterReq;
import com.mrchen.lottery.domain.rule.model.res.EngineResult;
import com.mrchen.lottery.domain.rule.model.vo.TreeNodeVO;
import com.mrchen.lottery.domain.rule.repository.IRuleRepository;
import com.mrchen.lottery.domain.rule.service.engine.EngineBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 规则引擎处理器
 * @author：cqh
 * @date: 2023/6/21
 */
@Service("ruleEngineHandle")
public class RuleEngineHandle extends EngineBase {
    @Resource
    private IRuleRepository ruleRepository;

    @Override
    public EngineResult process(DecisionMatterReq matter) {
        // 查数据库，获得决策树
        TreeRuleRich treeRuleRich = ruleRepository.queryRuleTreeRich(matter.getTreeId());
        if (null == treeRuleRich){
            throw new RuntimeException("Tree Rule is null");
        }

        // 决策节点
        TreeNodeVO treeNodeInfo = engineDecisionMaker(treeRuleRich, matter);

        // 决策结果
        return new EngineResult(matter.getUserId(), matter.getTreeId(), treeNodeInfo.getTreeNodeId(),
                treeNodeInfo.getNodeValue());
    }
}
