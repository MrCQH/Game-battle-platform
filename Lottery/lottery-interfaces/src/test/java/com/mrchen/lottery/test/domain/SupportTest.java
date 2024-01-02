package com.mrchen.lottery.test.domain;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.support.ids.IIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 支撑领域测试
 * @author：cqh
 * @date: 2023/6/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupportTest {
    private Logger logger = LoggerFactory.getLogger(SupportTest.class);

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Test
    public void test_ids(){
        logger.info("雪花算法策略， 生成ID：{}", idGeneratorMap.get(Constants.Ids.SnowFlake).nextId());
        logger.info("日期算法策略， 生成ID：{}", idGeneratorMap.get(Constants.Ids.ShortCode).nextId());
        logger.info("随机算法策略， 生成ID：{}", idGeneratorMap.get(Constants.Ids.RandomNumeric).nextId());
    }

    @Test
    public void test_snowFlake(){
        Snowflake snowflake;

        long workerId;
        try{
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e){
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        System.out.println(workerId);

        workerId = workerId >> 16 & 31;

        System.out.println(workerId);

        long dataCenterId = 1L;

        snowflake = IdUtil.getSnowflake(workerId, dataCenterId);

        System.out.println(snowflake.nextId());

    }
}
