package com.mrchen.lottery.domain.activity.model.vo;


/**
 * @description: 奖品信息配置
 * @author：cqh
 * @date: 2023/6/11
 */

public class AwardVO {
    private String awardId;
    /** 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品） */
    private Integer awardType;
    private String awardName;
    /** 奖品内容「描述、奖品码、sku」 */
    private String awardContent;

    public AwardVO() {
    }

    public AwardVO(String awardId, Integer awardType, String awardName, String awardContent) {
        this.awardId = awardId;
        this.awardType = awardType;
        this.awardName = awardName;
        this.awardContent = awardContent;
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

    @Override
    public String toString() {
        return "AwardVO{" +
                "awardId='" + awardId + '\'' +
                ", awardType=" + awardType +
                ", awardName='" + awardName + '\'' +
                ", awardContent='" + awardContent + '\'' +
                '}';
    }
}
