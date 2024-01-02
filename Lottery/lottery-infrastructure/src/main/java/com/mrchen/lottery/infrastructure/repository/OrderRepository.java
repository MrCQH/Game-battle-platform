package com.mrchen.lottery.infrastructure.repository;

import com.mrchen.lottery.domain.award.model.aggregates.AwardInfoLimitPageRich;
import com.mrchen.lottery.domain.award.model.req.AwardInfoLimitPageReq;
import com.mrchen.lottery.domain.award.repository.IOrderRepository;
import com.mrchen.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.mrchen.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.mrchen.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 奖品表仓储服务
 * @author：cqh
 * @date: 2023/6/7
 */
@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);
    }

    @Override
    public AwardInfoLimitPageRich queryAwardInfoLimitPage(AwardInfoLimitPageReq req) {
        Long count = userStrategyExportDao.queryAwardInfoCount(req);
        List<UserStrategyExport> list = userStrategyExportDao.queryAwardInfoList(req);

        List<DrawAwardVO> drawAwardVOList = new ArrayList<>();
        for (UserStrategyExport userStrategyExport : list){
            DrawAwardVO drawAwardVO = new DrawAwardVO();
            drawAwardVO.setuId(userStrategyExport.getuId());
            drawAwardVO.setAwardId(userStrategyExport.getAwardId());
            drawAwardVO.setAwardType(userStrategyExport.getAwardType());
            drawAwardVO.setAwardName(userStrategyExport.getAwardName());
            drawAwardVO.setAwardContent(userStrategyExport.getAwardContent());
            drawAwardVO.setGrantType(userStrategyExport.getGrantType());
            drawAwardVO.setGrantDate(userStrategyExport.getGrantDate());

            drawAwardVOList.add(drawAwardVO);
        }
        return new AwardInfoLimitPageRich(count, drawAwardVOList);
    }


}
