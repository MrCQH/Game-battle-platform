package com.mrchen.lottery.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import com.mrchen.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import com.mrchen.lottery.domain.activity.model.req.PartakeReq;
import com.mrchen.lottery.domain.activity.model.vo.*;
import com.mrchen.lottery.domain.activity.repository.IActivityRepository;
import com.mrchen.lottery.infrastructure.dao.*;
import com.mrchen.lottery.infrastructure.po.*;
import com.mrchen.lottery.infrastructure.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 活动服务
 * @author：cqh
 * @date: 2023/6/11
 */
@Repository
public class ActivityRepository implements IActivityRepository {

    private Logger logger = LoggerFactory.getLogger(ActivityRepository.class);

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IAwardDao awardDao;

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void addActivity(ActivityVO activity) {
        Activity req = new Activity();
        BeanUtils.copyProperties(activity, req);
        activityDao.insert(req);
    }

    @Override
    public void addAward(List<AwardVO> awardList) {
        List<Award> req = new ArrayList<>();
        for (AwardVO awardVO : awardList){
            Award award = new Award();
            BeanUtils.copyProperties(awardVO, award);
            req.add(award);
        }
        awardDao.insertList(req);
    }

    @Override
    public void addStrategy(StrategyVO strategy) {
        Strategy req = new Strategy();
        BeanUtils.copyProperties(strategy, req);
        strategyDao.insert(req);
    }

    @Override
    public void addStrategyDetailList(List<StrategyDetailVO> strategyDetailList) {
        List<StrategyDetail> req = new ArrayList<>();
        for (StrategyDetailVO strategyDetailVO : strategyDetailList){
            StrategyDetail strategyDetail = new StrategyDetail();
            BeanUtils.copyProperties(strategyDetailVO, strategyDetail);
            req.add(strategyDetail);
        }
        strategyDetailDao.insertList(req);
    }

    public boolean alterStatus(Long activityId, Enum<Constants.ActivityState> beforeState,
                               Enum<Constants.ActivityState> afterState) {
        AlterStateVO alterStateVO =
                new AlterStateVO(activityId, ((Constants.ActivityState)beforeState).getCode(),
                        ((Constants.ActivityState)afterState).getCode());
        // 乐观锁的思想
        int count = activityDao.alterState(alterStateVO);
        return 1 == count;
    }

    @Override
    public ActivityBillVO queryActivityBill(PartakeReq req) {

        // 查询活动信息
        QueryWrapper<Activity> activityQueryWrapper = new QueryWrapper<>();
        activityQueryWrapper.eq("activity_id", req.getActivityId());
        Activity activity = activityDao.selectOne(activityQueryWrapper);

        // 从缓存中获取已经使用的库存，及时更新当前库存量，防止超卖现象发生
        Object usedStockCountObj =  redisUtil.get(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(req.getActivityId()));

        // 查询领取次数
        UserTakeActivityCount userTakeActivityCountReq = new UserTakeActivityCount();
        userTakeActivityCountReq.setuId(req.getuid());
        userTakeActivityCountReq.setActivityId(req.getActivityId());
        UserTakeActivityCount userTakeActivityCount = userTakeActivityCountDao.queryUserTakeActivityCount(userTakeActivityCountReq);

        // 封装结果信息
        ActivityBillVO activityBillVO = new ActivityBillVO();
        activityBillVO.setuId(req.getuid());
        activityBillVO.setActivityId(req.getActivityId());
        activityBillVO.setActivityName(activity.getActivityName());
        activityBillVO.setBeginDateTime(activity.getBeginDateTime());
        activityBillVO.setEndDateTime(activity.getEndDateTime());
        activityBillVO.setTakeCount(activity.getTakeCount());
        activityBillVO.setStockCount(activity.getStockCount());
        // 库存剩余
        activityBillVO.setStockSurplusCount(null == usedStockCountObj ? activity.getStockSurplusCount() : activity.getStockCount() - Integer.parseInt(String.valueOf(usedStockCountObj)));
        activityBillVO.setStrategyId(activity.getStrategyId());
        activityBillVO.setState(activity.getState());
        activityBillVO.setUserTakeLeftCount(null == userTakeActivityCount ? null : userTakeActivityCount.getLeftCount());

        return activityBillVO;
    }

    @Override
    public int subtractionActivityStock(Long activityId) {
        return activityDao.subtractionActivityStock(activityId);
    }

    @Override
    public List<ActivityVO> scanToDoActivityList(Long id) {
        List<Activity> activityList = activityDao.scanToDoActivityList(id);
        List<ActivityVO> activityVOList = new ArrayList<>(activityList.size());
        for (Activity activity : activityList) {
            ActivityVO activityVO = new ActivityVO();
            activityVO.setActivityId(activity.getId());
            activityVO.setActivityId(activity.getActivityId());
            activityVO.setActivityName(activity.getActivityName());
            activityVO.setBeginDateTime(activity.getBeginDateTime());
            activityVO.setEndDateTime(activity.getEndDateTime());
            activityVO.setState(activity.getState());
            activityVOList.add(activityVO);
        }
        return activityVOList;
    }

    @Override
    public StockResult subtractionActivityStockByRedis(String uId, Long activityId, Integer stockCount) {
        //  1. 获取 已经领取的库存 Key
        String stockKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityId);

        //  2. 扣减库存，目前占用库存数
        Integer stockUsedCount = (int) redisUtil.incr(stockKey, 1);

        //  3. 超出库存，判断恢复原始库存
        if (stockUsedCount > stockCount){
            redisUtil.decr(stockKey, 1);
            return new StockResult(Constants.ResponseCode.OUT_OF_STOCK.getCode(), Constants.ResponseCode.OUT_OF_STOCK.getInfo());
        }

        //  4. 以活动库存占用编号，生成对应加锁Key，细化锁的颗粒度
        String stockTokenKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN(activityId, stockUsedCount);

        // 5. 使用 Redis.setNx 加一个分布式锁，原子的
        boolean lockToken = redisUtil.setNx(stockTokenKey, 350L);
        if (!lockToken){
            logger.info("抽奖活动{}用户秒杀{}扣减库存，分布式锁失败：{}", activityId, uId, stockTokenKey);
            return new StockResult(Constants.ResponseCode.ERR_TOKEN.getCode(), Constants.ResponseCode.ERR_TOKEN.getInfo());
        }

        return new StockResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(),
                stockTokenKey, stockCount - stockUsedCount);
    }

    @Override
    public void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        // 删除分布式锁 Key
        redisUtil.del(tokenKey);
    }

    @Override
    public ActivityInfoLimitPageRich queryActivityInfoLimitPage(ActivityInfoLimitPageReq req) {
        Long count = activityDao.queryActivityInfoCount(req);
        List<Activity> list = activityDao.queryActivityInfoList(req);

        List<ActivityVO> activityVOList = new ArrayList<>();
        for (Activity activity : list) {
            ActivityVO activityVO = new ActivityVO();
            activityVO.setId(activity.getId());
            activityVO.setActivityId(activity.getActivityId());
            activityVO.setActivityName(activity.getActivityName());
            activityVO.setActivityDesc(activity.getActivityDesc());
            activityVO.setBeginDateTime(activity.getBeginDateTime());
            activityVO.setEndDateTime(activity.getEndDateTime());
            activityVO.setStockCount(activity.getStockCount());
            activityVO.setStockSurplusCount(activity.getStockSurplusCount());
            activityVO.setTakeCount(activity.getTakeCount());
            activityVO.setStrategyId(activity.getStrategyId());
            activityVO.setState(activity.getState());
            activityVO.setCreator(activity.getCreator());
            activityVO.setCreateTime(activity.getCreateTime());
            activityVO.setUpdateTime(activity.getUpdateTime());

            activityVOList.add(activityVO);
        }

        return new ActivityInfoLimitPageRich(count, activityVOList);
    }

}

