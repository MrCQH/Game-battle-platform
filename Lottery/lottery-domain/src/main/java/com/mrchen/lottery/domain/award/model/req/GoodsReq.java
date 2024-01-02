package com.mrchen.lottery.domain.award.model.req;

import com.mrchen.lottery.domain.award.model.vo.ShippingAddress;


/**
 * @description: 奖品发货信息
 * @author：cqh
 * @date: 2023/6/7
 */

public class GoodsReq {
    private String uId;
    private Long orderId;
    private String awardId;
    private String awardName;
    /**
     * 奖品内容 「描述、奖品码、sku」
     */
    private String awardContent;
    /** 四级送货地址（只有实物类商品需要地址） */
    private ShippingAddress shippingAddress;
    /** 扩展信息，用于一些个性商品发放所需要的透传字段内容 */
    private String extInfo;

    public GoodsReq(String uId, Long orderId, String awardId, String awardName, String awardContent) {
        this.uId = uId;
        this.orderId = orderId;
        this.awardId = awardId;
        this.awardName = awardName;
        this.awardContent = awardContent;
    }

    public GoodsReq() {
    }

    public GoodsReq(String uId, Long orderId, String awardId, String awardName, String awardContent, ShippingAddress shippingAddress, String extInfo) {
        this.uId = uId;
        this.orderId = orderId;
        this.awardId = awardId;
        this.awardName = awardName;
        this.awardContent = awardContent;
        this.shippingAddress = shippingAddress;
        this.extInfo = extInfo;
    }

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
