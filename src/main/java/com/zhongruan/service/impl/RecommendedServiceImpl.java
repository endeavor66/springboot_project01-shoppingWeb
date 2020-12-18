package com.zhongruan.service.impl;

import com.zhongruan.bean.Brand;
import com.zhongruan.bean.Goods;
import com.zhongruan.dao.BrandDao;
import com.zhongruan.dao.GoodsDao;
import com.zhongruan.service.RecommendedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendedServiceImpl implements RecommendedService {
    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private BrandDao brandDao;

    /**
     * 查询某品牌销量最佳的top商品
     * @param i
     * @param brandId
     * @return
     */
    @Override
    public List<Goods> findSalesTop(int i, Long brandId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "sale");
        Pageable pageable = PageRequest.of(0,i,sort);
        return goodsDao.findSalesTop(brandId,pageable);
    }

    /**
     * 查询销量最佳的top商品
     * @param i
     * @return
     */
    @Override
    public List<Goods> findSalesTop(int i) {
        Sort sort = Sort.by(Sort.Direction.DESC, "sale");
        Pageable pageable = PageRequest.of(0,i,sort);
        return goodsDao.findSalesTop(pageable);
    }

    /**
     * 查询浏览量最多的top商品
     * @param i
     * @return
     */
    @Override
    public List<Goods> findViewsTop(int i) {
        Sort sort = Sort.by(Sort.Direction.DESC, "views");
        Pageable pageable = PageRequest.of(0,i,sort);
        return goodsDao.findViewsTop(pageable);
    }

    /**
     * 查询某品牌浏览量最多的top商品
     * @param i
     * @param brandId
     * @return
     */
    @Override
    public List<Goods> findViewsTop(int i, Long brandId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "views");
        Pageable pageable = PageRequest.of(0,i,sort);
        return goodsDao.findViewsTop(brandId,pageable);
    }

    @Override
    public List<Long> findBrandByViews(int i) {
        Sort sort = Sort.by(Sort.Direction.DESC, "views");
        Pageable pageable = PageRequest.of(0,i,sort);
        return goodsDao.findBrandByViews(pageable);
    }

    @Override
    public List<Brand> findBybrandId(List<Long> brandIds) {
        List<Brand> brands=new ArrayList<Brand>();
        for (Long brandId : brandIds) {
            brands.add(brandDao.findBybrandId(brandId));
        }
        return brands;
    }


}
