package com.zhongruan.service;

import com.zhongruan.bean.Brand;
import com.zhongruan.bean.Goods;
import com.zhongruan.bean.GoodsQuery;
import com.zhongruan.bean.Tag;
import com.zhongruan.util.MyBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GoodsService {
    
    Goods findById(Long id);

    void update(Goods goods);

    //明朗
    int getSaleTotal();

    int getViewTotal();

    int getIncomeTotal();

    //璐姐
    List<Goods> findSaleTopByBrandId(int i, Long brandId);

    //密姐
    Page<Goods> searchGoods(Pageable pageable, Long  id);
    Page<Goods> searchGoods(Pageable pageable, GoodsQuery newsQuery);
    Page<Goods> findTop(Pageable pageable, String s);
    Page<Goods> findAll(String query, Pageable pageable);
    Page<Goods> findAll(Pageable pageable);

    //春玲
    void add(Goods goods);

    void deleteById(Long id);

    String findTagIds(List<Tag> tags);

    Page<Goods> searchNews(Pageable pageable, GoodsQuery goodsQuery);

    List<String> findGroupYear();
}
