package com.mrchen.lottery.test.Dubbo;

import com.alibaba.fastjson.JSON;
import com.mrchen.lottery.rpc.activity.booth.ILotteryActivityBooth;
import com.mrchen.lottery.rpc.activity.booth.req.DrawReq;
import com.mrchen.lottery.rpc.activity.booth.res.DrawRes;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author：cqh
 * @date: 2023/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class APITest {
    private Logger logger = LoggerFactory.getLogger(APITest.class);

    @DubboReference
    private ILotteryActivityBooth lotteryActivityBooth;

    @Test
    public void testRPC(){
        DrawReq drawReq = new DrawReq();
        drawReq.setActivityId(100001L);
        drawReq.setuId("mrchen");
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        logger.info("测试结果: {}", JSON.toJSONString(drawRes));
    }
}
