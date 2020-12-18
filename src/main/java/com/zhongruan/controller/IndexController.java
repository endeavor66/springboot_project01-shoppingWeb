package com.zhongruan.controller;

import com.zhongruan.bean.Brand;
import com.zhongruan.bean.Goods;
import com.zhongruan.service.RecommendedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private RecommendedService recommendedService;

    @RequestMapping
    public String index(){
        return "redirect:toIndex?brandId=-1";
    }

    @RequestMapping("toIndex")
    public String toIndex(Long brandId, Model model){
        List<Goods> saleTopGoodsList = new ArrayList<Goods>();
        List<Goods> viewsTopGoodsList = new ArrayList<Goods>();
        if(brandId==-1){
            //默认显示浏览器量top4【所有品牌】
            saleTopGoodsList = recommendedService.findSalesTop(8);
            viewsTopGoodsList = recommendedService.findViewsTop(8);
        }
        else {
            saleTopGoodsList = recommendedService.findSalesTop(8,brandId);
            viewsTopGoodsList =recommendedService.findViewsTop(8,brandId);
        }
        model.addAttribute("saleTopGoodsList",saleTopGoodsList);
        model.addAttribute("viewsTopGoodsList",viewsTopGoodsList);
        //按照浏览量查找top4的品牌
        List<Long> brandIds=recommendedService.findBrandByViews(4);
        List<Brand> brandList=recommendedService.findBybrandId(brandIds);
        model.addAttribute("brandList",brandList);
        return "index";
    }
}
