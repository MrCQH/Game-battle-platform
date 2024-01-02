package com.mrchen.lottery.domain.strategy.service.draw;

import com.mrchen.lottery.domain.strategy.annotation.StrategyMode;
import com.mrchen.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mrchen
 */
public class DrawConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    /** 抽奖策略组 */
    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        Map<String, Object> strategyModeMap = applicationContext.getBeansWithAnnotation(StrategyMode.class);
        strategyModeMap.values().forEach(v -> {
            StrategyMode strategyMode = AnnotationUtils.findAnnotation(v.getClass(), StrategyMode.class);
            if (v instanceof IDrawAlgorithm){
                assert strategyMode != null;
                drawAlgorithmGroup.put(strategyMode.strategyMode().getId(), (IDrawAlgorithm) v);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
