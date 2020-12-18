package com.zhongruan.dao;

import com.zhongruan.bean.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandDao extends JpaRepository<Brand,Long> {
    //密姐
    @Query("SELECT t from Brand t")
    List<Brand> findTop(Pageable pageable);

    //璐姐
    @Query("select t from Brand t where t.id=?1")
    Brand findBybrandId(Long brandId);

    //明朗
    @Query("SELECT SUM(t.sale) FROM Brand t")
    int countOfSale();
}
