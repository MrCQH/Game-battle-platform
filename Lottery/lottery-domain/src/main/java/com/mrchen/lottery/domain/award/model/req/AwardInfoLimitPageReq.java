package com.mrchen.lottery.domain.award.model.req;

import com.mrchen.lottery.common.PageRequest;

/**
 * @description: 奖品分页查询请求对象
 * @author: mrchen
 * @date: 2023/11/9
 */
public class AwardInfoLimitPageReq extends PageRequest {
    private String uId;

    public AwardInfoLimitPageReq() {
    }

    public AwardInfoLimitPageReq(String page, String rows) {
        super(page, rows);
    }

    public AwardInfoLimitPageReq(int page, int rows) {
        super(page, rows);
    }

    public AwardInfoLimitPageReq(String uId) {
        this.uId = uId;
    }

    public AwardInfoLimitPageReq(String page, String rows, String uId) {
        super(page, rows);
        this.uId = uId;
    }

    public AwardInfoLimitPageReq(int page, int rows, String uId) {
        super(page, rows);
        this.uId = uId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "AwardInfoLimitPageReq{" +
                super.toString() +
                "uId='" + uId + '\'' +
                '}';
    }
}
