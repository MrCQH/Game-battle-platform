package com.mrchen.lottery.test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.award.model.req.GoodsReq;
import com.mrchen.lottery.domain.award.model.res.DistributionRes;
import com.mrchen.lottery.domain.award.service.factory.DistributionGoodsFactory;
import com.mrchen.lottery.domain.award.service.goods.IDistributionGoods;
import com.mrchen.lottery.domain.strategy.model.req.DrawReq;
import com.mrchen.lottery.domain.strategy.model.res.DrawResult;
import com.mrchen.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.mrchen.lottery.domain.strategy.service.draw.IDrawExec;
import com.mrchen.lottery.infrastructure.dao.IActivityDao;
import com.mrchen.lottery.infrastructure.po.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRunnerTest {

    private final Logger logger = LoggerFactory.getLogger(SpringRunnerTest.class);

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IDrawExec drawExec;

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @Test
    public void test_drawExec() {
        drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));
        drawExec.doDrawExec(new DrawReq("小佳佳", 10001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 10001L));
    }

    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100002L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockSurplusCount(100);
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setStrategyId(10002L);
        activity.setState(5);
        activity.setCreator("mrchen");
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        activityDao.insert(activity);
    }

    @Test
    public void test_select() {
        QueryWrapper<Activity> activityQueryWrapper = new QueryWrapper<>();
        activityQueryWrapper.eq("activity_id", 100002L);
        Activity activity = activityDao.selectOne(activityQueryWrapper);
        logger.info("测试结果：{}", JSON.toJSONString(activity));
    }

    @Test
    public void test_award(){
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq("mrchen", 10001L));

        Integer drawState = drawResult.getDrawState();
        if (Constants.DrawState.FAIL.getCode().equals(drawState)){
            logger.info("未中奖 DrawAwardInfo is null");
            return;
        }

        DrawAwardVO drawAwardInfo = drawResult.getDrawAwardInfo();
        GoodsReq goodsReq =
                new GoodsReq(drawResult.getuId(), 2109313442431L, drawResult.getDrawAwardInfo().getAwardId(),
                drawResult.getDrawAwardInfo().getAwardName(), drawResult.getDrawAwardInfo().getAwardContent());

        // 根据 awardType 从抽奖工厂中获取对应的发奖服务
        IDistributionGoods distributionGoodsService =
                distributionGoodsFactory.getDistributionGoodsService(drawAwardInfo.getAwardType());
        DistributionRes distributionRes = distributionGoodsService.doDistribution(goodsReq);
        logger.info("测试结果：{}", JSON.toJSONString(distributionRes));
    }
}
