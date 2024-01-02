package com.mrchen.lottery.rpc.activity.booth.res;

import com.mrchen.lottery.common.Result;
import com.mrchen.lottery.rpc.activity.booth.dto.AwardDTO;

import java.io.Serializable;

/**
 * @description: 抽奖结果
 * @author：cqh
 * @date: 2023/6/21
 */
public class DrawRes extends Result implements Serializable {
    private AwardDTO awardDTO;

    public DrawRes(String code, String info) {
        super(code, info);
    }

    public AwardDTO getAwardDTO() {
        return awardDTO;
    }

    public void setAwardDTO(AwardDTO awardDTO) {
        this.awardDTO = awardDTO;
    }

    @Override
    public String toString() {
        return "DrawRes{" +
                "awardDTO=" + awardDTO +
                '}';
    }
}
