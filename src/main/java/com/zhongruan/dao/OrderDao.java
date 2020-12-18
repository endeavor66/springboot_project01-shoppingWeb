package com.zhongruan.dao;

import com.zhongruan.bean.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Modifying
    @Query("update Order o set o.commentState = 1 , o.comment = ?2 where o.id = ?1 ")
    void addComment(Long id, String comment);
}
