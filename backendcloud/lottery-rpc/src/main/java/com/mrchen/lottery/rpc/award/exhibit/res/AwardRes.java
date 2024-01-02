package com.mrchen.lottery.rpc.award.exhibit.res;

import com.mrchen.lottery.common.Result;
import com.mrchen.lottery.rpc.award.exhibit.dto.AwardDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 奖励查询结果
 */
public class AwardRes implements Serializable {
    Result result;
    private Long count;
    private List<AwardDTO> awardDTOList;

    public AwardRes() {
    }

    public AwardRes(Result result, Long count, List<AwardDTO> awardDTOList) {
        this.result = result;
        this.count = count;
        this.awardDTOList = awardDTOList;
    }

    public AwardRes(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<AwardDTO> getAwardDTOList() {
        return awardDTOList;
    }

    public void setAwardDTOList(List<AwardDTO> awardDTOList) {
        this.awardDTOList = awardDTOList;
    }
}
