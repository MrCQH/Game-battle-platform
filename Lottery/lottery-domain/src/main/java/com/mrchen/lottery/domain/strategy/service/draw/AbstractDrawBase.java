package com.mrchen.lottery.domain.strategy.service.draw;

import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.mrchen.lottery.domain.strategy.model.req.DrawReq;
import com.mrchen.lottery.domain.strategy.model.res.DrawResult;
import com.mrchen.lottery.domain.strategy.model.vo.*;
import com.mrchen.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 抽奖过程,模版模式
 * @author：cqh
 * @date: 2023/5/20
 */
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec{
    private final Logger logger = LoggerFactory.getLogger(AbstractDrawBase.class);

    @Override
    public DrawResult doDrawExec(DrawReq req) {
        // 1.获取抽奖策略
        StrategyRich strategyRich = super.queryStrategyRich(req.getStrategyId());
        StrategyBriefVO strategy = strategyRich.getStrategy();

        // 2. 校验抽奖策略，是否已经初始化到内存，把抽奖的商品数据加载到内存
        this.checkAndInitRateData(req.getStrategyId(), strategy.getStrategyMode(), strategyRich.getStrategyDetailList());

        // 3. 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等 TODO：这一步一个减少一次查询，因为前面已经提前查出来了所有的数据
        List<String> excludeAwardIds = this.queryExcludeAwardIds(req.getStrategyId());

        // 4. 执行抽奖算法
        String awardId = this.drawAlgorithm(req.getStrategyId(),
                drawAlgorithmGroup.get(strategy.getStrategyMode()), excludeAwardIds);

        // 5. 包装结果
        return buildDrawResult(req.getuId(), req.getStrategyId(), awardId, strategy);
    }

    /**
     * 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等，这类数据是含有业务逻辑的，所以需要由具体的实现方决定
     *
     * @param strategyId 策略ID
     * @return 排除的奖品ID集合
     */
    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * 执行抽奖算法
     *
     * @param strategyId        抽奖策略ID
     * @param drawAlgorithm     抽奖算法模型
     * @param excludeAwardIds   排除的抽奖ID集合
     * @return 中奖奖品ID
     */
    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds);

    /**
     *
     * @param strategyId            抽奖策略ID
     * @param strategyMode          抽奖模式
     * @param strategyDetailList    抽奖策略详情
     */
    private void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetailBriefVO> strategyDetailList){
        // 非单项概率，不必存入缓存
        if (!Constants.StrategyMode.SINGLE.getCode().equals(strategyMode)) {
            return;
        }

        IDrawAlgorithm drawAlgorithm = drawAlgorithmGroup.get(strategyMode);

        // 已初始化过的数据，不必重复初始化
        if (drawAlgorithm.isExistRateTuple(strategyId)) {
            return;
        }

        // 解析并初始化中奖概率数据到散列表
        List<AwardRateVO> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetailBriefVO strategyDetail : strategyDetailList){
            awardRateInfoList.add(new AwardRateVO(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);
    }

    private DrawResult buildDrawResult(String uId, Long strategyId, String awardId, StrategyBriefVO strategy){
        if (awardId == null){
            logger.info("执行策略抽奖完成[未中奖]， 用户: {} 策略Id {}", uId, strategyId);
            return new DrawResult(uId, strategyId, Constants.DrawState.FAIL.getCode());
        }

        AwardBriefVO award = super.queryAwardInfoByAwardId(awardId);
        DrawAwardVO drawAwardInfo = new DrawAwardVO(uId, award.getAwardId(), award.getAwardType(), award.getAwardName(),
                award.getAwardContent());
        drawAwardInfo.setStrategyMode(strategy.getStrategyMode());
        drawAwardInfo.setGrantType(strategy.getGrantType());
        drawAwardInfo.setGrantDate(strategy.getGrantDate());
        drawAwardInfo.setAwardContent(drawAwardInfo.getAwardContent());
        drawAwardInfo.setAwardId(drawAwardInfo.getAwardId());
        logger.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId,
                award.getAwardName());

        return new DrawResult(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), drawAwardInfo);
    }
}
