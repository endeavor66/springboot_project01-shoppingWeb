package com.zhongruan.service;

import com.zhongruan.bean.Brand;
import com.zhongruan.bean.Goods;

import java.util.List;

public interface RecommendedService {

    List<Goods> findSalesTop(int i, Long brandId);
    List<Goods> findSalesTop(int i);

    List<Goods> findViewsTop(int i);
    List<Goods> findViewsTop(int i, Long brandId);

    List<Long> findBrandByViews(int i);


    List<Brand> findBybrandId(List<Long> brandIds);
}
