package com.mrchen.lottery.test.domain;

import com.alibaba.fastjson.JSON;
import com.mrchen.lottery.domain.rule.model.req.DecisionMatterReq;
import com.mrchen.lottery.domain.rule.model.res.EngineResult;
import com.mrchen.lottery.domain.rule.service.engine.EngineFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @description: 规则引擎测试
 * @author：cqh
 * @date: 2023/6/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RuleTest {
    private Logger logger = LoggerFactory.getLogger(RuleTest.class);

    @Resource
    private EngineFilter engineFilter;

    @Test
    public void test_process(){
        DecisionMatterReq req = new DecisionMatterReq();
        req.setTreeId(2110081902L);
        req.setUserId("mrchen");
        req.setValMap(new HashMap<String, Object>(){{
            put("gender", "man");
            put("age", "25");
        }});

        EngineResult res = engineFilter.process(req);

        logger.info("请求参数：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }
}
