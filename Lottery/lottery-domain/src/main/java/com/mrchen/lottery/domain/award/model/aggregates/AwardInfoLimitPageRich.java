package com.mrchen.lottery.domain.award.model.aggregates;

import com.mrchen.lottery.domain.strategy.model.vo.DrawAwardVO;

import java.util.List;

/**
 * @description: 奖品分页查询聚合对象
 * @author: mrchen
 * @date: 2023/11/10
*/
public class AwardInfoLimitPageRich {
    private Long count;
    private List<DrawAwardVO> drawAwardVOList;

    public AwardInfoLimitPageRich() {
    }

    public AwardInfoLimitPageRich(Long count, List<DrawAwardVO> drawAwardVOList) {
        this.count = count;
        this.drawAwardVOList = drawAwardVOList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<DrawAwardVO> getDrawAwardVOList() {
        return drawAwardVOList;
    }

    public void setDrawAwardVOList(List<DrawAwardVO> drawAwardVOList) {
        this.drawAwardVOList = drawAwardVOList;
    }

    @Override
    public String toString() {
        return "AwardInfoLimitPageRich{" +
                super.toString() +
                "count=" + count +
                ", drawAwardVOList=" + drawAwardVOList +
                '}';
    }
}
