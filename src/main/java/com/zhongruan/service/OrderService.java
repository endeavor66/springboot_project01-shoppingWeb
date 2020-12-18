package com.zhongruan.service;

import com.zhongruan.bean.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order findById(long id);

    Page<Order> findByCondition(Pageable pageable, QueryOrder queryOrder);

    void updateOrder(Order order);

    void delete(Long id);

    void addComment(Long id, String comment);

    void addOrder(User user, Goods goods);

    String getOrderIds(List<Order> orderList);

    void updateAfterPay(String orderIds, Long addressId);
}
