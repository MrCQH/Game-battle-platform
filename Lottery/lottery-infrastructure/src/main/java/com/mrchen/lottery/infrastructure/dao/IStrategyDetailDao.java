package com.mrchen.lottery.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrchen.lottery.infrastructure.po.StrategyDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyDetailDao extends BaseMapper<StrategyDetail> {
    /**
     * 插入策略配置组
     *
     * @param list 策略配置组
     */
    void insertList(List<StrategyDetail> list);
}
