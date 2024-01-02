package com.mrchen.lottery.test.domain;

import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.support.ids.IIdGenerator;
import com.mrchen.lottery.infrastructure.dao.IUserTakeActivityDao;
import com.mrchen.lottery.infrastructure.po.UserTakeActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.Date;
import java.util.Map;

/**
 * @description: 测试用户领取活动表
 * @author：cqh
 * @date: 2023/6/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTakeActivityDaoTest {
    private Logger logger = LoggerFactory.getLogger(UserTakeActivityDaoTest.class);

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    private IUserTakeActivityDao userTakeActivityDao;

    @Test
    public void test_insert(){
        for (int i = 0; i < 10; i ++){
            String uid = String.valueOf(idGeneratorMap.get(Constants.Ids.RandomNumeric).nextId());
            UserTakeActivity userTakeActivity = new UserTakeActivity();
            userTakeActivity.setuId(uid); // 库1. Uhdgkw766120d 库2.
            userTakeActivity.setTakeId(121019889416L + i);
            userTakeActivity.setActivityId(100007L + i);
            userTakeActivity.setActivityName("测试活动");
            userTakeActivity.setTakeDate(new Date());
            userTakeActivity.setTakeCount(10);
            userTakeActivity.setUuid(uid);
            userTakeActivityDao.insert(userTakeActivity);
        }

    }
}
