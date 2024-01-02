package com.mrchen.lottery.domain.award.model.vo;



/**
 * @description: 实物商品送货四级地址
 * @author：cqh
 * @date: 2023/6/7
 */
public class ShippingAddress {
    /**
     * 名称
     */
    private String name;
    /**
     * 省份id
     */
    private String provinceId;
    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 城镇id
     */
    private String townId;
    /**
     * 城镇名称
     */
    private String townName;
    /**
     * 住址
     */
    private String address;
    /**
     * 电话
     */
    private String phone;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 备注
     */
    private String remark;

    public ShippingAddress() {
    }

    public ShippingAddress(String name, String provinceId, String provinceName,
                           String cityId, String townId, String townName, String address, String phone, String email, String remark) {
        this.name = name;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.cityId = cityId;
        this.townId = townId;
        this.townName = townName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
