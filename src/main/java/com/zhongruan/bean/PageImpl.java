package com.zhongruan.bean;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageImpl {
    Pageable pageable;
    long total;
    List<Goods> content;

    public PageImpl(List<Goods> content, Pageable pageable, long total) {
        this.content = content;
        this.pageable = pageable;
        this.total = total;
    }


}
