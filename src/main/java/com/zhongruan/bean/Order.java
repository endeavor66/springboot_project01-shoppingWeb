package com.zhongruan.bean;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;    //创建时间，即商品加入购物车的时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime;   //付款时间
    private Integer purchase;   //购买数量
    private Float payment;      //付款金额
    private Boolean payState;   //付款状态

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentTime;   //评论时间
    private String comment;  //评论内容
    private boolean commentState;   //评论状态

    @ManyToOne
    private User user;

    @ManyToOne
    private Goods goods;

    @ManyToOne
    private Address address;

    public Order() {
    }

    public Order(Long id, Integer purchase, Float payment) {
        this.id = id;
        this.purchase = purchase;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Boolean getPayState() {
        return payState;
    }

    public void setPayState(Boolean payState) {
        this.payState = payState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isCommentState() {
        return commentState;
    }

    public void setCommentState(boolean commentState) {
        this.commentState = commentState;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", purchase=" + purchase +
                ", payment=" + payment +
                ", payState=" + payState +
                ", commentTime=" + commentTime +
                ", comment='" + comment + '\'' +
                ", commentState=" + commentState +
                '}';
    }
}
