package com.mrchen.lottery.domain.rule.service.logic.Impl;

import com.mrchen.lottery.domain.rule.model.req.DecisionMatterReq;
import com.mrchen.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * @description: 年龄规则
 * @author：cqh
 * @date: 2023/6/21
 */
@Component
public class UserAgeFilter extends BaseLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("age").toString();
    }
}
