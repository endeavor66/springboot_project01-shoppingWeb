package com.zhongruan.service.impl;

import com.zhongruan.bean.*;
import com.zhongruan.dao.AddressDao;
import com.zhongruan.dao.GoodsDao;
import com.zhongruan.dao.OrderDao;
import com.zhongruan.service.OrderService;
import com.zhongruan.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    AddressDao addressDao;

    @Override
    public Order findById(long id) {
        return orderDao.getOne(id);
    }

    @Override
    public Page<Order> findByCondition(Pageable pageable, QueryOrder queryOrder) {
        Specification<Order> specification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                //若支付状态不为空，则纳入查询条件集合
                if(queryOrder.getPayState()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("payState"), queryOrder.getPayState()));
                }
                //若评论状态不为空，则纳入查询条件集合
                if(queryOrder.getCommentState()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("commentState"), queryOrder.getCommentState()));
                }
                if(queryOrder.getUid()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Long>get("user").get("id"), queryOrder.getUid()));
                }
                if(queryOrder.getGid()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Long>get("goods").get("id"), queryOrder.getGid()));
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        //使用查询条件集合，完成数据库的动态查询
        Page<Order> page = orderDao.findAll(specification, pageable);
        return page;
    }

    /**
     * 更新订单：购买量、应付金额
     * @param order
     */
    @Override
    public void updateOrder(Order order) {
        Order o = orderDao.getOne(order.getId());
        //覆盖更新
        BeanUtils.copyProperties(order, o, MyBeanUtils.getNullPropertyNames(order));
        orderDao.save(o);
    }

    @Override
    public void delete(Long id) {
        orderDao.deleteById(id);
    }

    /**
     * 添加评论
     * @param id    订单id
     * @param comment   评论内容
     */
    @Override
    public void addComment(Long id, String comment) {
        Order order = new Order();
        //设置更新字段
        order.setId(id);
        order.setComment(comment);
        order.setCommentState(true);
        order.setCommentTime(new Date());
        //查询数据库中的该订单的信息，对订单评论状态、评论时间、评论内容进行更新
        Order o = orderDao.getOne(id);
        BeanUtils.copyProperties(order, o, MyBeanUtils.getNullPropertyNames(order));
        orderDao.save(o);
    }

    @Override
    public void addOrder(User user, Goods goods) {
        Order order = new Order();
        order.setUser(user);
        order.setGoods(goods);
        order.setCreateTime(new Date());
        order.setPurchase(1);
        order.setPayment(goods.getPrice());
        order.setPayState(false);
        order.setCommentState(false);
        orderDao.save(order);
    }

    @Override
    public String getOrderIds(List<Order> orderList) {
        StringBuffer buffer = new StringBuffer();
        boolean flag = false;
        for (Order order : orderList) {
            if(flag){
                buffer.append(","+order.getId());
            }else{
                buffer.append(order.getId());
                flag=true;
            }
        }
        return buffer.toString();
    }

    /**
     * 支付成功后更新订单、商品信息
     * @param orderIds
     */
    @Override
    public void updateAfterPay(String orderIds, Long addressId) {
        //字符串分割得到订单的id集合
        String[] ids = orderIds.split(",");
        for (String id : ids) {
            //更新订单的付款时间、付款状态、评论状态
            Order order = new Order();
            order.setPayState(true);
            order.setPayTime(new Date());
            order.setCommentState(false);
            order.setAddress(addressDao.getOne(addressId));
            Order o = orderDao.getOne(Long.parseLong(id));
            BeanUtils.copyProperties(order, o, MyBeanUtils.getNullPropertyNames(order));
            //更新商品库存、销量
            int purchase = o.getPurchase();
            Goods goods = o.getGoods();
            goods.setStock(goods.getStock() - o.getPurchase());
            goods.setSale(goods.getSale() + o.getPurchase());
            //更新brand的sale
            Brand brand = goods.getBrand();
            brand.setSale(brand.getSale() + purchase);
            goods.setBrand(brand);
            //数据最后更新，若前面出错，不会更新数据库
            orderDao.save(o);
            goodsDao.save(goods);
        }
    }
}
