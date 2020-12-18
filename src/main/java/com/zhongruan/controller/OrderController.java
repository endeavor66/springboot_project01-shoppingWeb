package com.zhongruan.controller;

import com.alibaba.fastjson.JSONArray;
import com.zhongruan.bean.*;
import com.zhongruan.service.AddressService;
import com.zhongruan.service.GoodsService;
import com.zhongruan.service.OrderService;
import com.zhongruan.service.UserService;
import com.zhongruan.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AddressService addressService;

    /**
     * 查询所有订单，综合了如下几种查询：
     * 1. 未付款
     * 2. 付款未评论
     * 3. 付款已评论
     * 使用：借助queryOrder，封装了查询条件。
     * 效果：查询结果按照 createTime倒序，即新创建的订单在靠上显示
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping("allOrder")
    public String allOrder(@PageableDefault(size = 5, sort = {"createTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                           QueryOrder queryOrder, Model model, HttpSession session){
        //1. 检查用户是否登录
        User user = (User) session.getAttribute("user");
        if(user==null){
            System.out.println("您尚未登录，请先登录");
            return "login";
        }else{
            //2. 从order表中查询所有该用户的订单
            queryOrder.setUid(user.getId());
            Page<Order> page = orderService.findByCondition(pageable, queryOrder);
            model.addAttribute("page", page);
            if(queryOrder.getPayState()==null){
                //查询所有订单
                return "allOrder";
            }else{
                if(queryOrder.getPayState()==true){
                    if(queryOrder.getCommentState()==null){
                        //查询已付款
                        return "paidOrder";
                    }else{
                        //查询待评论
                        return "unAppraiseOrder";
                    }
                }else{
                    //查询待付款
                    return "shoppingCart";
                }
            }
        }
    }

    @RequestMapping("toShopCart")
    public String toShopCart(){
        return "redirect:allOrder?payState=0";
    }

    /**
     * 加入购物车
     * @param gid
     * @param session
     * @return
     */
    @RequestMapping("addToCart")
    public String addToCart(Long gid, Model model ,HttpSession session){
        //1. 判断当前用户是否登录
        User user = (User) session.getAttribute("user");
        if(user==null){
            System.out.println("您尚未登录，请先登录");
            return "login";
        }else {
            //2. 从order表中查找uid,gid,且payState=false的订单，若没有，则成功添加；否则提示已添加至购物车
            QueryOrder queryOrder = new QueryOrder();
            queryOrder.setUid(user.getId());
            queryOrder.setGid(gid);
            queryOrder.setPayState(false);
            Pageable pageable = PageRequest.of(0, 5);
            Page<Order> page = orderService.findByCondition(pageable, queryOrder);
            //3. 根据查询设置返回体
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setMsg(gid.toString());  //将商品id放在resultInfo中
            if(page.isEmpty()){
                //创建新订单，成功添加至购物车
                Goods goods = goodsService.findById(gid);
                orderService.addOrder(user, goods);
                resultInfo.setFlag(true);
            }else if(page.getContent().size() == 1){
                //若存在该订单，则直接购买数+1
                Order order = page.getContent().get(0);
                order.setPurchase(order.getPurchase() + 1);
                orderService.updateOrder(order);
                resultInfo.setFlag(true);
            }else{
                //存在多笔重复的订单
                resultInfo.setFlag(false);
            }
            model.addAttribute("resultInfo", resultInfo);
            return "order-addToCartResult";   //返回订单管理主界面-默认全部订单
        }
    }

    /**
     * 响应前台提交订单的ajax请求：
     * 1. 更新order信息：购买数量、应付款金额
     * 2. 将用户的所有订单id，用户id存入map返回
     * @param orders
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("toPay")
    public Map<String, Object> toPay(String orders, HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Order> orderList = JSONArray.parseArray(orders, Order.class);
        for (Order order : orderList) {
            orderService.updateOrder(order);
        }
        String orderIds = orderService.getOrderIds(orderList);
        //使用hashMap保存结果
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", user.getId());
        map.put("orderIds", orderIds);
        return map;
    }

    /**
     * 跳转到结算页
     * @param orderIds
     * @param totalPrice
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("toSettle")
    public String toSettle(String orderIds, Float totalPrice, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
        List<Address> addressList = addressService.findByUid(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("orderIds", orderIds);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("addressList",addressList);
        return "order-settlement";
    }

    @PostMapping("settle")
    public String settle(Long userId, String orderIds, Float totalPrice, Long addressId, String payPassword, Model model){
        //1. 验证payPassword
        User user = userService.findById(userId);
        ResultInfo resultInfo = new ResultInfo(); //存放处理结果
        if(!StringUtils.isEmpty(payPassword) && user.getPayPassword().equals(MD5Util.code(payPassword))){
            //校验余额
            Float money = user.getMoney();
            if(money >= totalPrice){
                //更新订单、商品信息
                orderService.updateAfterPay(orderIds, addressId);
                //更新用户余额
                user.setMoney(money - totalPrice);
                userService.update(user);
                //添加支付信息
                resultInfo.setFlag(true);
                resultInfo.setMsg(totalPrice.toString());
                model.addAttribute("resultInfo", resultInfo);
                return "order-payResult";
            }else{
                resultInfo.setFlag(false);
                resultInfo.setMsg("余额不足");
                model.addAttribute("resultInfo", resultInfo);
                return "order-payResult";
            }
        }else{
            resultInfo.setFlag(false);
            resultInfo.setMsg("支付密码错误");
            model.addAttribute("resultInfo", resultInfo);
            return "order-payResult";
        }
    }


    /**
     * 删除未付款订单（从购物车中删除）,未付款订单都不可能有评论
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteOrder")
    public String deleteOrder(String ids){
        String[] idList = ids.split(",");
        for (String id : idList) {
            orderService.delete(Long.parseLong(id));
        }
        return "shoppingCart";
    }

    /**
     * 进入商品详情页
     * @param pageable
     * @param gid
     * @param model
     * @return
     */
    @RequestMapping("toGoodsDetail")
    public String allComment(@PageableDefault(size = 5, sort = {"commentTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                             Long gid, Model model){
        //根据gid查询该商品的所有评论
        QueryOrder queryOrder = new QueryOrder();
        queryOrder.setGid(gid);
        queryOrder.setPayState(true);
        queryOrder.setCommentState(true);
        Page<Order> page = orderService.findByCondition(pageable, queryOrder);
        //根据gid查询该商品详细信息
        Goods goods = goodsService.findById(gid);
        //更新商品浏览量
        goods.setViews(goods.getViews()+1);
        goodsService.update(goods);
        //根据gid查询该商品所属品牌的销量最佳的top3商品，用作商品推荐
        List<Goods> top = goodsService.findSaleTopByBrandId(3, goods.getBrand().getId());
        model.addAttribute("page", page);
        model.addAttribute("goods", goods);
        model.addAttribute("top", top);
        return "single";
    }

    @RequestMapping("toComment")
    public String toComment(Long id, Model model ,HttpSession session){
        Order order = orderService.findById(id);
        User user = (User) session.getAttribute("user");
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        return "appraise";
    }

    /**
     * 已付款订单添加评论
     * @param orderId
     * @param comment
     * @return
     */
    @RequestMapping("addComment")
    public String addComment(Long orderId, String comment){
        //添加评论
        orderService.addComment(orderId, comment);
        return "order-commentResult";
    }

}
