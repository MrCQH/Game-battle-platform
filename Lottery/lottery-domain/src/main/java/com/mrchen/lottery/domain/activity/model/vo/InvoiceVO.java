package com.mrchen.lottery.domain.activity.model.vo;

import com.mrchen.lottery.domain.award.model.vo.ShippingAddress;

/**
 * @description: 中奖物品发货单，用于发送MQ消息，异步触达发货奖品给用户
 * @author：cqh
 * @date: 2023/6/23
 */
public class InvoiceVO {
    private String uId;
    private Long orderId;
    private String awardId;
    private Integer awardType;
    private String awardName;
    private String awardContent;
    private ShippingAddress shippingAddress;
    private String extInfo;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public Integer getAwardType() {
        return awardType;
    }

    public void setAwardType(Integer awardType) {
        this.awardType = awardType;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardContent() {
        return awardContent;
    }

    public void setAwardContent(String awardContent) {
        this.awardContent = awardContent;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
