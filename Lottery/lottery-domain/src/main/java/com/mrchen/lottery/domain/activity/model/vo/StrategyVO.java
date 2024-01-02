package com.mrchen.lottery.domain.activity.model.vo;



import java.util.Date;
import java.util.List;

/**
 * @description: 策略信息配置
 * @author：cqh
 * @date: 2023/6/11
 */
public class StrategyVO {
    private Long strategyId;
    private String strategyDesc;
    // 策略方式「1:单项概率、2:总体概率」
    private Integer strategyMode;
    // 发放奖品方式「1:即时、2:定时[含活动结束]、3:人工」
    private Integer grantType;
    // 发放奖品时间
    private Date grantDate;
    private String extInfo;
    /**
     * 策略详情配置
     */
    private List<StrategyDetailVO> strategyDetailList;

    public StrategyVO() {
    }

    public StrategyVO(Long strategyId, String strategyDesc, Integer strategyMode, Integer grantType,
                      Date grantDate, String extInfo, List<StrategyDetailVO> strategyDetailList) {
        this.strategyId = strategyId;
        this.strategyDesc = strategyDesc;
        this.strategyMode = strategyMode;
        this.grantType = grantType;
        this.grantDate = grantDate;
        this.extInfo = extInfo;
        this.strategyDetailList = strategyDetailList;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyDesc() {
        return strategyDesc;
    }

    public void setStrategyDesc(String strategyDesc) {
        this.strategyDesc = strategyDesc;
    }

    public Integer getStrategyMode() {
        return strategyMode;
    }

    public void setStrategyMode(Integer strategyMode) {
        this.strategyMode = strategyMode;
    }

    public Integer getGrantType() {
        return grantType;
    }

    public void setGrantType(Integer grantType) {
        this.grantType = grantType;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public List<StrategyDetailVO> getStrategyDetailList() {
        return strategyDetailList;
    }

    public void setStrategyDetailList(List<StrategyDetailVO> strategyDetailList) {
        this.strategyDetailList = strategyDetailList;
    }

    @Override
    public String toString() {
        return "StrategyVO{" +
                "strategyId=" + strategyId +
                ", strategyDesc='" + strategyDesc + '\'' +
                ", strategyMode=" + strategyMode +
                ", grantType=" + grantType +
                ", grantDate=" + grantDate +
                ", extInfo='" + extInfo + '\'' +
                ", strategyDetailList=" + strategyDetailList +
                '}';
    }
}