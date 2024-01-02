package com.mrchen.lottery.application.process.draw.res;

import com.mrchen.lottery.common.Result;
import com.mrchen.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * @description: 抽奖结果
 * @author：cqh
 * @date: 2023/6/20
 */
public class DrawProcessResult extends Result {
    private DrawAwardVO drawAwardVO;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardVO drawAwardVO) {
        super(code, info);
        this.drawAwardVO = drawAwardVO;
    }

    public DrawAwardVO getDrawAwardVO() {
        return drawAwardVO;
    }

    public void setDrawAwardVO(DrawAwardVO drawAwardVO) {
        this.drawAwardVO = drawAwardVO;
    }
}
