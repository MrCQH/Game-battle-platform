package com.mrchen.lottery.domain.activity.service.partake.impl;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.common.Result;
import com.mrchen.lottery.domain.activity.model.req.PartakeReq;
import com.mrchen.lottery.domain.activity.model.vo.*;
import com.mrchen.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.mrchen.lottery.domain.activity.service.partake.BaseActivityPartake;
import com.mrchen.lottery.domain.support.ids.IIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 活动参与功能实现
 * @author：cqh
 * @date: 2023/6/19
 */
@Service
public class ActivityPartakeImpl extends BaseActivityPartake {
    private Logger logger = LoggerFactory.getLogger(ActivityPartakeImpl.class);

    @Resource
    private IUserTakeActivityRepository userTakeActivityRepository;

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    // 事务
    @Resource
    private TransactionTemplate transactionTemplate;

    // 数据库路由
    @Resource
    private IDBRouterStrategy dbRouter;

    @Override
    protected UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        return userTakeActivityRepository.queryNoConsumedTakeActivityOrder(activityId, uId);
    }

    @Override
    protected Result checkActivityBill(PartakeReq partake, ActivityBillVO bill) {
        // 校验: 活动状态
        if (!Constants.ActivityState.DOING.getCode().equals(bill.getState())){
            logger.warn("活动当前状态非可用 state: {}", bill.getState());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动状态非可用");
        }

        // 校验：活动日期
        if (bill.getBeginDateTime().after(partake.getPartakeDate()) || bill.getEndDateTime().before(partake.getPartakeDate())){
            logger.warn("活动时间范围非可用 beginDateTime: {} endDateTime: {}", bill.getBeginDateTime(), bill.getEndDateTime());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动时间范围非可用");
        }

        // 校验：活动库存
        if (bill.getStockSurplusCount() <= 0){
            logger.warn("活动剩余库存非可用 stockSurplusCount: {}", bill.getStockSurplusCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动剩余库存非可用");
        }

        // 校验：个人库存 - 个人活动剩余可领取次数
        if (null != bill.getUserTakeLeftCount() && bill.getUserTakeLeftCount() <= 0){
            logger.warn("个人领取次数非可用 userTakeLeftCount: {}", bill.getUserTakeLeftCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动领取次数非可用");
        }

        return Result.buildSuccessResult();
    }

    @Override
    protected Result subtractionActivityStock(PartakeReq req) {
        int count = activityRepository.subtractionActivityStock(req.getActivityId());
        if (0 == count) {
            logger.error("扣减活动库存失败 activityId：{}", req.getActivityId());
            return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
        }
        return Result.buildSuccessResult();
    }

    @Override
    protected StockResult subtractionActivityStockByRedis(String uId, Long activityId, Integer stockCount) {
        return activityRepository.subtractionActivityStockByRedis(uId, activityId, stockCount);
    }

    @Override
    protected void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        activityRepository.recoverActivityCacheStockByRedis(activityId, tokenKey, code);
    }

    // 一个声明事务, 组件里已经讲 transactionTemplate 注册到Bean中
    @Override
    protected Result grabActivity(PartakeReq partake, ActivityBillVO bill, Long takeId) {
        try{
            // 手动设置路由， 那uId做路由，散列到一个库里
            dbRouter.doRouter(partake.getuid());
            return transactionTemplate.execute(status -> {
                try{
                  // 扣减个人已参与的次数
                  int updateCount =
                          userTakeActivityRepository.subtractionLeftCount(bill.getActivityId(), bill.getActivityName(),
                                  bill.getTakeCount(), /** 已经领取次数 */ bill.getUserTakeLeftCount(), partake.getuid(), partake.getPartakeDate());
                  if (0 == updateCount){
                      status.setRollbackOnly();
                      logger.error("领取活动扣减个人已参与次数失败 activityId: {}, uId: {}", partake.getActivityId(), partake.getuid());
                      return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                  }

                  // 插入领取活动信息
                    userTakeActivityRepository.takeActivity(bill.getActivityId(), bill.getActivityName(), bill.getStrategyId(),
                            bill.getTakeCount(), bill.getUserTakeLeftCount(), partake.getuid(), partake.getPartakeDate(), takeId);
                } catch (DuplicateKeyException e){
                    status.setRollbackOnly();
                    logger.error("领取活动，唯一索引冲突 activityId: {}, uId: {}", partake.getActivityId(), partake.getuid());
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
                }
                return Result.buildSuccessResult();
            });
        } finally {
            dbRouter.clear();
        }
    }

    @Override
    public Result recordDrawOrder(DrawOrderVO drawOrder) {
        try{
            dbRouter.doRouter(drawOrder.getuId());
            return transactionTemplate.execute(status -> {
               try {
                   // 更新活动领取记录
                   int lockCount = userTakeActivityRepository.lockTakeActivity(drawOrder.getuId(), drawOrder.getActivityId(),
                           drawOrder.getTakeId());
                   if (0 == lockCount){
                       status.setRollbackOnly();
                       logger.error("记录中奖单， 个人参与活动已消耗完 activityId: {}, uId: {}", drawOrder.getActivityId(), drawOrder.getuId());
                       return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                   }

                   // 保存抽奖信息，开发票order
                   userTakeActivityRepository.saveUserStrategyExport(drawOrder);
               } catch (DuplicateKeyException e){
                   status.setRollbackOnly();
                   logger.error("记录中奖单，唯一素银冲突 activityId: {}, uId: {}", drawOrder.getActivityId(), drawOrder.getuId());
                   return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
               }
               return Result.buildSuccessResult();
            });
        } finally {
            dbRouter.clear();
        }
    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState(int dbCount, int tbCount) {
        try {
            // 设置路由
            dbRouter.setDBKey(dbCount);
            dbRouter.setTBKey(tbCount);

            // 查询数据
            return userTakeActivityRepository.scanInvoiceMqState();
        } finally {
            dbRouter.clear();
        }
    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        userTakeActivityRepository.updateInvoiceMqState(uId, orderId, mqState);
    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        userTakeActivityRepository.updateActivityStock(activityPartakeRecordVO);
    }

}
