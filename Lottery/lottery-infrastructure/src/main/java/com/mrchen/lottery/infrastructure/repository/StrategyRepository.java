package com.mrchen.lottery.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mrchen.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.mrchen.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.mrchen.lottery.domain.strategy.model.vo.StrategyBriefVO;
import com.mrchen.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import com.mrchen.lottery.domain.strategy.repository.IStrategyRepository;
import com.mrchen.lottery.infrastructure.dao.IAwardDao;
import com.mrchen.lottery.infrastructure.dao.IStrategyDao;
import com.mrchen.lottery.infrastructure.dao.IStrategyDetailDao;
import com.mrchen.lottery.infrastructure.po.Award;
import com.mrchen.lottery.infrastructure.po.Strategy;
import com.mrchen.lottery.infrastructure.po.StrategyDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    IAwardDao awardDao;

    @Resource
    IStrategyDao strategyDao;

    @Resource
    IStrategyDetailDao strategyDetailDao;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        // Wrapper
        QueryWrapper<Strategy> strategyQueryWrapper = new QueryWrapper<>();
        QueryWrapper<StrategyDetail> strategyDetailQueryWrapper = new QueryWrapper<>();

        // condition
        strategyQueryWrapper.eq("strategy_id", strategyId);
        strategyDetailQueryWrapper.eq("strategy_id", strategyId);

        // result
        Strategy strategy = strategyDao.selectOne(strategyQueryWrapper);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.selectList(strategyDetailQueryWrapper);

        // copy attribute
        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        List<StrategyDetailBriefVO> strategyDetailBriefVOList = new ArrayList<>();
        BeanUtils.copyProperties(strategy, strategyBriefVO);
        for (StrategyDetail strategyDetail : strategyDetailList){
            StrategyDetailBriefVO strategyDetailBriefVO = new StrategyDetailBriefVO();
            BeanUtils.copyProperties(strategyDetail, strategyDetailBriefVO);
            strategyDetailBriefVOList.add(strategyDetailBriefVO);
        }

        return new StrategyRich(strategyId, strategyBriefVO, strategyDetailBriefVOList);
    }

    @Override
    public AwardBriefVO queryAwardInfo(String awardId) {
        // wrapper
        QueryWrapper<Award> awardQueryWrapper = new QueryWrapper<>();

        // condition
        awardQueryWrapper.eq("award_id", awardId);

        // 可以使用 BeanUtils.copyProperties(award, awardBriefVO)、
        // 或者基于ASM实现的Bean-Mapping，但在效率上最好的依旧是硬编码
        Award award = awardDao.selectOne(awardQueryWrapper);
        AwardBriefVO awardBriefVO = new AwardBriefVO();
        awardBriefVO.setAwardId(award.getAwardId());
        awardBriefVO.setAwardType(award.getAwardType());
        awardBriefVO.setAwardName(award.getAwardName());
        awardBriefVO.setAwardContent(award.getAwardContent());

        return awardBriefVO;
    }


    @Override
    public List<String> queryNoStockStrategyAwardList(Long strategyId) {
        QueryWrapper<StrategyDetail> strategyDetailQueryWrapper = new QueryWrapper<>();
        // condition
        strategyDetailQueryWrapper
                .eq("strategy_id", strategyId)
                .eq("award_surplus_count", 0);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.selectList(strategyDetailQueryWrapper);

        return strategyDetailList.stream()
                .map(StrategyDetail::getAwardId).collect(Collectors.toList());
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
        UpdateWrapper<StrategyDetail> strategyDetailUpdateWrapper = new UpdateWrapper<>();
        /*
            UPDATE strategy_detail SET award_surplus_count = award_surplus_count - 1
            WHERE strategy_id = #{strategyId} AND award_id = #{awardId} AND award_surplus_count > 0
         */
        strategyDetailUpdateWrapper
                .setSql("award_surplus_count = award_surplus_count - 1")
                .eq("strategy_id", strategyId)
                .eq("award_id", awardId)
                .gt("award_surplus_count", 0);
        return strategyDetailDao.update(null, strategyDetailUpdateWrapper) == 1;
    }
}
