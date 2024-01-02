package com.mrchen.lottery.infrastructure.repository;

import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import com.mrchen.lottery.domain.activity.model.vo.DrawOrderVO;
import com.mrchen.lottery.domain.activity.model.vo.InvoiceVO;
import com.mrchen.lottery.domain.activity.model.vo.UserTakeActivityVO;
import com.mrchen.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.mrchen.lottery.domain.support.ids.policy.SnowFlake;
import com.mrchen.lottery.infrastructure.dao.IActivityDao;
import com.mrchen.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.mrchen.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import com.mrchen.lottery.infrastructure.dao.IUserTakeActivityDao;
import com.mrchen.lottery.infrastructure.po.Activity;
import com.mrchen.lottery.infrastructure.po.UserStrategyExport;
import com.mrchen.lottery.infrastructure.po.UserTakeActivity;
import com.mrchen.lottery.infrastructure.po.UserTakeActivityCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 用户参与活动仓储
 * @author：cqh
 * @date: 2023/6/20
 */
@Repository
public class UserTakeActivityRepository implements IUserTakeActivityRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;

    @Resource
    private IUserTakeActivityDao userTakeActivityDao;

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Resource
    private IActivityDao activityDao;

    @Resource
    private SnowFlake snowFlake;

    @Override
    public int subtractionLeftCount(Long activityId, String activityName, Integer takeCount,
                                    Integer userTakeLeftCount, String uId, Date partakeDate) {
        // 用户第一次参与
        if (null == userTakeLeftCount){
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setId(snowFlake.nextId());
            userTakeActivityCount.setuId(uId);
            userTakeActivityCount.setActivityId(activityId);
            /**
             * 可领取次数
             */
            userTakeActivityCount.setTotalCount(takeCount);
            /**
             * 已领取次数
             */
            userTakeActivityCount.setLeftCount(takeCount - 1);
            userTakeActivityCountDao.insert(userTakeActivityCount);
            return 1;
        } else {
            // 不是第一次参与
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setuId(uId);
            userTakeActivityCount.setActivityId(activityId);
            return userTakeActivityCountDao.updateLeftCount(userTakeActivityCount);
        }
    }

    @Override
    public int lockTakeActivity(String uId, Long activityId, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setuId(uId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setTakeId(takeId);
        return userTakeActivityDao.lockTakeActivity(userTakeActivity);
    }

    @Override
    public void takeActivity(Long activityId, String activityName, Long strategyId, Integer takeCount,
                             Integer userTakeLeftCount, String uId, Date takeDate, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setId(snowFlake.nextId());
        userTakeActivity.setuId(uId);
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setActivityName(activityName);
        userTakeActivity.setTakeDate(takeDate);
        if (null == userTakeLeftCount){
            userTakeActivity.setTakeCount(1);
        } else {
            userTakeActivity.setTakeCount(takeCount - userTakeLeftCount + 1);
        }
        userTakeActivity.setStrategyId(strategyId);
        userTakeActivity.setState(Constants.TaskState.NO_USED.getCode());
        String uuid = uId + "_" + activityId + "_" + userTakeActivity.getTakeCount();
        userTakeActivity.setUuid(uuid);

        userTakeActivityDao.insert(userTakeActivity);
    }

    @Override
    public UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUuid(uId);
        userTakeActivity.setActivityId(activityId);
        UserTakeActivity noConsumedTakeActivityOrder = userTakeActivityDao.queryNoConsumedTakeActivityOrder(userTakeActivity);

        if (null == noConsumedTakeActivityOrder){
            return null;
        }

        UserTakeActivityVO userTakeActivityVO = new UserTakeActivityVO();
        userTakeActivity.setActivityId(noConsumedTakeActivityOrder.getActivityId());
        userTakeActivityVO.setTakeId(noConsumedTakeActivityOrder.getTakeId());
        userTakeActivityVO.setStrategyId(noConsumedTakeActivityOrder.getStrategyId());
        userTakeActivityVO.setState(noConsumedTakeActivityOrder.getState());
        return userTakeActivityVO;
    }

    @Override
    public void saveUserStrategyExport(DrawOrderVO drawOrder) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();

        long snowId = snowFlake.nextId();
        userStrategyExport.setId(snowId);
        logger.info("雪花ID, UserStrategyExport id: {}", snowId);

        userStrategyExport.setuId(drawOrder.getuId());
        userStrategyExport.setActivityId(drawOrder.getActivityId());
        userStrategyExport.setOrderId(drawOrder.getOrderId());
        userStrategyExport.setStrategyId(drawOrder.getStrategyId());
        userStrategyExport.setStrategyMode(drawOrder.getStrategyMode());
        userStrategyExport.setGrantType(drawOrder.getGrantType());
        userStrategyExport.setGrantDate(drawOrder.getGrantDate());
        userStrategyExport.setGrantState(drawOrder.getGrantState());
        userStrategyExport.setAwardId(drawOrder.getAwardId());
        userStrategyExport.setAwardType(drawOrder.getAwardType());
        userStrategyExport.setAwardName(drawOrder.getAwardName());
        userStrategyExport.setAwardContent(drawOrder.getAwardContent());
        userStrategyExport.setMqState(Constants.MQState.INIT.getCode());
        // 设置数据库的uuid为unique, 保证该方法的幂等性
        userStrategyExport.setUuid(String.valueOf(drawOrder.getTakeId()));

        userStrategyExportDao.insert(userStrategyExport);
    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setMqState(mqState);
        userStrategyExportDao.updateInvoiceMqState(userStrategyExport);
    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState() {
        // 查询发送MQ失败和超时30分钟，未发送MQ的数据
        List<UserStrategyExport> userStrategyExportList = userStrategyExportDao.scanInvoiceMqState();
        // 转换对象
        List<InvoiceVO> invoiceVOList = new ArrayList<>(userStrategyExportList.size());
        for (UserStrategyExport userStrategyExport : userStrategyExportList) {
            InvoiceVO invoiceVO = new InvoiceVO();
            invoiceVO.setuId(userStrategyExport.getuId());
            invoiceVO.setOrderId(userStrategyExport.getOrderId());
            invoiceVO.setAwardId(userStrategyExport.getAwardId());
            invoiceVO.setAwardType(userStrategyExport.getAwardType());
            invoiceVO.setAwardName(userStrategyExport.getAwardName());
            invoiceVO.setAwardContent(userStrategyExport.getAwardContent());
            invoiceVOList.add(invoiceVO);
        }
        return invoiceVOList;
    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        Activity activity = new Activity();
        activity.setActivityId(activityPartakeRecordVO.getActivityId());
        activity.setStockSurplusCount(activityPartakeRecordVO.getStockSurplusCount());
        activityDao.updateActivityStock(activity);
    }
}
