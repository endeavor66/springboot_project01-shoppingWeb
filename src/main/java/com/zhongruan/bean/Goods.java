package com.zhongruan.bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_goods")
public class Goods {

    @Id
    @GeneratedValue
    private Long id;
    private String goodsName;   //商品名
    private String description; //描述
    private Float price;    //价格
    private Integer stock;  //库存
    private Integer views;  //浏览量
    private Integer sale;   //销售量
    private String picture;     //图片
    private String createTime;    //上架时间，即成功添加商品时间
    private String updateTime;    //更新时间

    @ManyToOne
    private Brand brand;    //所属品牌

    @Transient
    private String tagIds;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tagList = new ArrayList<Tag>();   //包含的标签，如：短袖可拥有的标签：男、休闲、潮流

    @OneToMany(mappedBy = "goods")
    private List<Order> orderList = new ArrayList<Order>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    //春玲
    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void init() {
        this.tagIds = tagsToIds(this.getTagList());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    //    @Override
//    public String toString() {
//        return "Goods{" +
//                "id=" + id +
//                ", goodsName='" + goodsName + '\'' +
//                ", description='" + description + '\'' +
//                ", price=" + price +
//                ", stock=" + stock +
//                ", views=" + views +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
//                ", brand=" + brand +
//                ", tagList=" + tagList +
//                ", pictureList=" + pictureList +
//                '}';
//    }
}
