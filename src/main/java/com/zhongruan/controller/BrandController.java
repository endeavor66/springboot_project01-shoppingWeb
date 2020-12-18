package com.zhongruan.controller;

import com.zhongruan.bean.Brand;
import com.zhongruan.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @RequestMapping
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Brand> page=brandService.findAll(pageable);
        model.addAttribute("page",page);
        return "admin/brand";
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        brandService.deleteById(id);
        return "redirect:/admin/brands";
    }

    @RequestMapping("toInput/{id}")
    public String toInput(@PathVariable Long id,Model model){
        if(id==-1){   //add
            model.addAttribute("brand",new Brand());
        }else{   //更新
            Brand brand=brandService.findById(id);
            model.addAttribute("brand",brand);
        }
        return "admin/brand-input";
    }

    @RequestMapping("input")
    public String input(Brand brand){
        brandService.add(brand);
        return "redirect:/admin/brands";
    }


}
