package com.kob.backend.model.user.account;

/**
 * 分页请求
 *
 **/
public class AwardLimitPageReq {
    /**
     * 谁的信息
     */
    private String uid;
    /**
     * 第几页
     */
    private String page;
    /**
     * 一页有几行
     */
    private String rows;

    public AwardLimitPageReq() {
    }

    public AwardLimitPageReq(String uid, String page, String rows) {
        this.uid = uid;
        this.page = page;
        this.rows = rows;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "AwardPageReq{" +
                "uid='" + uid + '\'' +
                ", page='" + page + '\'' +
                ", rows='" + rows + '\'' +
                '}';
    }
}
