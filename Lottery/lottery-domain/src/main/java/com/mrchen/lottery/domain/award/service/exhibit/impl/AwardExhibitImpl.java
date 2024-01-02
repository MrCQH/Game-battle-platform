package com.mrchen.lottery.domain.award.service.exhibit.impl;

import com.mrchen.lottery.domain.award.model.aggregates.AwardInfoLimitPageRich;
import com.mrchen.lottery.domain.award.model.req.AwardInfoLimitPageReq;
import com.mrchen.lottery.domain.award.repository.IOrderRepository;
import com.mrchen.lottery.domain.award.service.exhibit.IAwardExhibit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AwardExhibitImpl implements IAwardExhibit {
    @Resource
    private IOrderRepository orderRepository;

    @Override
    public AwardInfoLimitPageRich queryAwardInfoLimitPage(AwardInfoLimitPageReq req) {
        return orderRepository.queryAwardInfoLimitPage(req);
    }
}
