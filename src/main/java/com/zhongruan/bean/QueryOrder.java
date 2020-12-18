package com.zhongruan.bean;

public class QueryOrder {

    private Long id;

    private Long uid;

    private Long gid;

    private Boolean payState;

    private Boolean commentState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Boolean getPayState() {
        return payState;
    }

    public void setPayState(Boolean payState) {
        this.payState = payState;
    }

    public Boolean getCommentState() {
        return commentState;
    }

    public void setCommentState(Boolean commentState) {
        this.commentState = commentState;
    }


}
