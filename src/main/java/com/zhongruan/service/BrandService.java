package com.zhongruan.service;

import com.zhongruan.bean.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    List<Brand> findTop(int i);
    List<Brand> findAll();

    //春玲
    Page<Brand> findAll(Pageable pageable);

    void deleteById(Long id);

    Brand findById(Long id);

    void add(Brand brand);

    //明朗
    public List<Brand> findSaleTop(int i);

    int getTotalSale();
}
