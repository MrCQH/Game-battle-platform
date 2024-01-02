package com.mrchen.lottery.learning.AOP;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author：cqh
 * @date: 2023/7/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AOPTest {

    @Resource
    private IUserService userService;

    @Test
    public void test_AOP() {
        userService.register("小帅");
    }
}
