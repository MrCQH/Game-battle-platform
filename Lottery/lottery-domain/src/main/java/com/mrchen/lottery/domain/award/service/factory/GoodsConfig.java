package com.mrchen.lottery.domain.award.service.factory;

import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.award.service.goods.IDistributionGoods;
import com.mrchen.lottery.domain.award.service.goods.Impl.CouponGoods;
import com.mrchen.lottery.domain.award.service.goods.Impl.DescGoods;
import com.mrchen.lottery.domain.award.service.goods.Impl.PhysicalGoods;
import com.mrchen.lottery.domain.award.service.goods.Impl.RedeemCodeGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 各类发奖奖品配置类
 * @author：cqh
 * @date: 2023/6/7
 */
public class GoodsConfig {
    /** 奖品发放策略组 */
    protected Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    CouponGoods couponGoods;

    @Resource
    DescGoods descGoods;

    @Resource
    PhysicalGoods physicalGoods;

    @Resource
    RedeemCodeGoods redeemCodeGoods;

    @PostConstruct
    public void init(){
        goodsMap.put(Constants.AwardType.CouponGoods.getCode(), couponGoods);
        goodsMap.put(Constants.AwardType.DESC.getCode(), descGoods);
        goodsMap.put(Constants.AwardType.PhysicalGoods.getCode(), physicalGoods);
        goodsMap.put(Constants.AwardType.RedeemCodeGoods.getCode(), redeemCodeGoods);
    }
}
