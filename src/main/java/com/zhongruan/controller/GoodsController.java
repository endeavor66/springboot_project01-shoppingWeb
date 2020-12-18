package com.zhongruan.controller;

import com.zhongruan.bean.Brand;
import com.zhongruan.bean.Goods;
import com.zhongruan.bean.GoodsQuery;
import com.zhongruan.bean.Tag;
import com.zhongruan.service.BrandService;
import com.zhongruan.service.GoodsService;
import com.zhongruan.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/items")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private TagService tagService;

    @RequestMapping
    public String find(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model){
        Page<Goods> page=goodsService.findAll(pageable);
        List<String> updateYears=goodsService.findGroupYear();;
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands",brands);
        model.addAttribute("updateYears",updateYears);
        model.addAttribute("page",page);
        return "admin/item";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        goodsService.deleteById(id);
        return "redirect:/admin/items";
    }

    @RequestMapping("input/{id}")
    public String toInput(@PathVariable Long id,Model model){
        if(id==-1){
            model.addAttribute("goods",new Goods());
        }else {
            Goods goods=goodsService.findById(id);
            List<Tag> tags = goods.getTagList();
            String tagIds=goodsService.findTagIds(tags);
            goods.setTagIds(tagIds);
            model.addAttribute("goods",goods);
        }
        List<Brand> brands=brandService.findAll();
        List<Tag> tags=tagService.findAll();
        model.addAttribute("brands",brands);
        model.addAttribute("tags",tags);
        return "admin/item-input";
    }

    @RequestMapping("input")
    public String input(Goods goods){
        List<Tag> tags=tagService.findByIds(goods.getTagIds());
        goods.setTagList(tags);
        goods.setSale(0);
        goodsService.add(goods);
        return "redirect:/admin/items";

    }

    @RequestMapping("search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         GoodsQuery goodsQuery, Model model){
        Page<Goods> page=goodsService.searchNews(pageable,goodsQuery);
        model.addAttribute("page",page);
        return "admin/item :: itemsList";

    }

}
