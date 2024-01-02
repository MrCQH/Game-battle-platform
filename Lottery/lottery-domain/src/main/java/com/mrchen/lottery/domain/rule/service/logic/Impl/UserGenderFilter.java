package com.mrchen.lottery.domain.rule.service.logic.Impl;

import com.mrchen.lottery.domain.rule.model.req.DecisionMatterReq;
import com.mrchen.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * @description: 性别规则
 * @author：cqh
 * @date: 2023/6/21
 */
@Component
public class UserGenderFilter extends BaseLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }
}
