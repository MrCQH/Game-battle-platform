package com.mrchen.lottery.domain.strategy.annotation;

import com.mrchen.lottery.common.StrategyModeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author：cqh
 * @date: 2023/7/24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrategyMode {
    StrategyModeEnum strategyMode();
}
