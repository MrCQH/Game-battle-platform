package com.mrchen.lottery.learning.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author：cqh
 * @date: 2023/7/21
 */
@Aspect
@Component
public class TestBeforeAdvice{
    @Around("execution(* com.mrchen.lottery.learning.AOP.UserService.*(..)) && args(str)")
    public Object around(ProceedingJoinPoint point, String str) throws Throwable {
        System.out.println("前面的方法 " + str);
        Object res = point.proceed();
        System.out.println("后面的方法 " + str);
        return res;
    }
}