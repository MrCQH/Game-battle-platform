package com.mrchen.lottery.test;

import com.mrchen.lottery.infrastructure.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description: redis test
 * @author：cqh
 * @date: 2023/6/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void test_redis(){
        try{
            redisUtil.set("111", 111);
            System.out.println("true");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("false");
        }

    }
}
