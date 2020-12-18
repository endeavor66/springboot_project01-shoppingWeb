package com.zhongruan.dao;

import com.zhongruan.bean.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsDao extends JpaRepository<Goods,Long>, JpaSpecificationExecutor<Goods> {

    //明朗
    @Query("SELECT SUM(t.sale) FROM Goods t")
    int countOfSale();

    @Query("SELECT SUM(t.views) FROM Goods t")
    int countOfView();

    @Query("SELECT SUM(t.price*t.sale) FROM Goods t")
    int getIncome();

    //璐姐
    @Query("select t from Goods t where t.brand.id=?1")
    List<Goods> findSalesTop(Long brandId,Pageable pageable);

    @Query("select t from Goods t")
    List<Goods> findSalesTop(Pageable pageable);

    @Query("select t from Goods t")
    List<Goods> findViewsTop(Pageable pageable);

    @Query("select t from Goods t where t.brand.id=?1")
    List<Goods> findViewsTop(Long brandId, Pageable pageable);

    @Query("SELECT t.brand.id,sum(t.views) as totalviews from Goods t group by t.brand.id order by totalviews desc")
    List<Long> findBrandByViews(Pageable pageable);

    //密姐
    @Query("SELECT n FROM Goods n WHERE n.goodsName LIKE ?1 or n.description like ?1")
    Page<Goods> findByQuery(String s, Pageable pageable);

    //后来加的
    @Query("select function('date_format',g.updateTime,'%Y') as year from Goods g group by year order by year desc ")
    List<String> findGroupYear();

}
