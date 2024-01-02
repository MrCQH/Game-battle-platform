package com.mrchen.lottery.test.application;

import com.alibaba.fastjson2.JSON;
import com.mrchen.lottery.application.process.draw.IActivityProcess;
import com.mrchen.lottery.application.process.draw.req.DrawProcessReq;
import com.mrchen.lottery.application.process.draw.res.DrawProcessResult;
import com.mrchen.lottery.rpc.activity.booth.ILotteryActivityBooth;
import com.mrchen.lottery.rpc.activity.booth.req.QuantificationDrawReq;
import com.mrchen.lottery.rpc.activity.booth.res.DrawRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @description: 抽奖编排测试
 * @author：cqh
 * @date: 2023/6/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityProcessTest {
    private Logger logger = LoggerFactory.getLogger(ActivityProcessTest.class);

    @Resource
    private IActivityProcess activityProcess;

    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;

    @Test
    public void test_doProcess(){
        DrawProcessReq req = new DrawProcessReq();
        req.setuId("mrchen");
        req.setActivityId(100001L);
        DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);

        logger.info("请求入参：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(drawProcessResult));

    }

    @Test
    public void test_rule_process(){
        QuantificationDrawReq req = new QuantificationDrawReq();
        req.setTreeId(2110081902L);
        req.setuId("mrchen");
        req.setValMap(new HashMap<String, Object>(){{
            put("gender", "man");
            put("age", "24");
        }});
        DrawRes drawRes = lotteryActivityBooth.doQuantificationDraw(req);
        logger.info("请求入参：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(drawRes));
    }
}
