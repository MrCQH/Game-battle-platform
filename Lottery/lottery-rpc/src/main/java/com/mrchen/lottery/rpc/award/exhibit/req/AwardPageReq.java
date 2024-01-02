package com.mrchen.lottery.rpc.award.exhibit.req;

import com.mrchen.lottery.common.PageRequest;

import java.io.Serializable;

/**
 * 奖品分页请求
 */
public class AwardPageReq extends PageRequest implements Serializable {
    /**
     * 玩家id
     */
    private String uId;

    public AwardPageReq() {
    }

    public AwardPageReq(String page, String rows) {
        super(page, rows);
    }

    public AwardPageReq(int page, int rows) {
        super(page, rows);
    }

    public AwardPageReq(String uId) {
        this.uId = uId;
    }

    public AwardPageReq(String page, String rows, String uId) {
        super(page, rows);
        this.uId = uId;
    }

    public AwardPageReq(int page, int rows, String uId) {
        super(page, rows);
        this.uId = uId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
