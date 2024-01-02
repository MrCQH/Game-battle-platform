package com.mrchen.lottery.interfaces.controller;

import com.mrchen.lottery.rpc.activity.booth.ILotteryActivityBooth;
import com.mrchen.lottery.rpc.activity.booth.req.DrawReq;
import com.mrchen.lottery.rpc.activity.booth.res.DrawRes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

;

/**
 * @description: http 直接调用
 * @author：cqh
 * @date: 2023/7/8
 */
@RestController("/lottery")
public class LotteryActivityBoothController {
    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;

    @PostMapping("/do")
    public DrawRes doProcess(DrawReq drawReq){
        return lotteryActivityBooth.doDraw(drawReq);
    }
}
