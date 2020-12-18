package com.zhongruan.service.impl;

import com.zhongruan.bean.Brand;
import com.zhongruan.dao.BrandDao;
import com.zhongruan.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;
    @Override
    public List<Brand> findTop(int i) {
        Sort sort= Sort.by(Sort.Direction.DESC,"goodsList.size");
        Pageable pageable= PageRequest.of(0,i,sort);
        return brandDao.findTop(pageable);
    }

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    //春玲
    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandDao.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        brandDao.deleteById(id);
    }

    @Override
    public Brand findById(Long id) {
        return brandDao.getOne(id);
    }

    @Override
    public void add(Brand brand) {
        brandDao.save(brand);
    }

    @Override
    public List<Brand> findSaleTop(int i) {
        Sort sort=Sort.by(Sort.Direction.DESC,"sale");
        Pageable pageable= PageRequest.of(0,i,sort);
        return brandDao.findTop(pageable);
    }

    @Override
    public int getTotalSale() {
        return brandDao.countOfSale();
    }
}
