package com.mrchen.lottery.domain.rule.service.engine;

import com.mrchen.lottery.domain.rule.service.logic.Impl.UserAgeFilter;
import com.mrchen.lottery.domain.rule.service.logic.Impl.UserGenderFilter;
import com.mrchen.lottery.domain.rule.service.logic.LogicFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 规则配置
 * @author：cqh
 * @date: 2023/6/21
 */
public class EngineConfig {
    protected static Map<String, LogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    @Resource
    private UserAgeFilter userAgeFilter;

    @Resource
    private UserGenderFilter userGenderFilter;

    @PostConstruct
    public void init(){
        logicFilterMap.put("userAge", userAgeFilter);
        logicFilterMap.put("userGender", userGenderFilter);
    }

}
