package com.zhongruan.bean;

import java.util.List;

public class ResultInfo {

    private Boolean flag;
    private List<Long> lackIds;
    private String msg;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Long> getLackIds() {
        return lackIds;
    }

    public void setLackIds(List<Long> lackIds) {
        this.lackIds = lackIds;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                '}';
    }
}
