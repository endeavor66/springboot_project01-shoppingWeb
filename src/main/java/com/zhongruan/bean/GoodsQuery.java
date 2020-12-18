package com.zhongruan.bean;

public class GoodsQuery {
    //周密
    private String title;
    private String brandId;

    //春玲
    private String goodsName;
    private String updateYear;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    //春玲
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUpdateYear() {
        return updateYear;
    }

    public void setUpdateYear(String updateYear) {
        this.updateYear = updateYear;
    }

    @Override
    public String toString() {
        return "GoodsQuery{" +
                "title='" + title + '\'' +
                ", brandId='" + brandId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", updateYear='" + updateYear + '\'' +
                '}';
    }
}
