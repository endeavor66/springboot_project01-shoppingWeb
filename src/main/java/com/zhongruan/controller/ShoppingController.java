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
@RequestMapping("goods")
public class ShoppingController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private TagService tagService;

    /**
     * jewellery首页，展示包含商品数最多的top5标签、品牌，以及所有商品
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping
    public String index(@PageableDefault(size = 9,sort = {"views"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        Page<Goods> page = goodsService.findAll(pageable);
        List<Brand> brands=brandService.findTop(5);
        List<Tag> tags=tagService.findTop(5);
        model.addAttribute("brands",brands);
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        return "jewellery";
    }
    @RequestMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 9,sort = {"views"},direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id,Model model) {
        List<Tag> tags = tagService.findTop(tagService.findAll().size());
        List<Brand> brands=brandService.findTop(5);
        if (id == -1) {
            id = tags.get(0).getId();
        }
            Page<Goods> page = goodsService.searchGoods(pageable, id);
        model.addAttribute("brands",brands);
            model.addAttribute("page", page);
            model.addAttribute("tags", tags);
            return "jewellery";
    }
    @RequestMapping("/price/up")
    public String priceUp(@PageableDefault(size = 9,sort = {"price"},direction = Sort.Direction.ASC) Pageable pageable,
                        Model model){
        Page<Goods> page = goodsService.findAll(pageable);
        List<Brand> brands=brandService.findTop(5);
        List<Tag> tags=tagService.findTop(5);
        model.addAttribute("brands",brands);
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        return "jewellery";
    }
    @RequestMapping("/price/down")
    public String priceDown(@PageableDefault(size = 9,sort = {"price"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        Page<Goods> page = goodsService.findAll(pageable);
        List<Brand> brands=brandService.findTop(5);
        List<Tag> tags=tagService.findTop(5);
        model.addAttribute("brands",brands);
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        return "jewellery";
    }
    @RequestMapping("/views/up")
    public String viewsDownUp(@PageableDefault(size = 9,sort = {"views"},direction = Sort.Direction.ASC) Pageable pageable,
                            Model model){
        Page<Goods> page = goodsService.findAll(pageable);
        List<Brand> brands=brandService.findTop(5);
        List<Tag> tags=tagService.findTop(5);
        model.addAttribute("brands",brands);
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        return "jewellery";
    }
    @RequestMapping("/views/down")
    public String viewsDown(@PageableDefault(size = 9,sort = {"views"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        Page<Goods> page = goodsService.findAll(pageable);
        List<Brand> brands=brandService.findTop(5);
        List<Tag> tags=tagService.findTop(5);
        model.addAttribute("brands",brands);
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        return "jewellery";
    }
    @RequestMapping("/brands/{id}")
    public String brands(@PageableDefault(size = 9,sort = {"views"},direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id,Model model) {
        List<Brand> brands = brandService.findTop(brandService.findAll().size());
        List<Tag> tags=tagService.findTop(5);
        if (id == -1) {
            id = brands.get(0).getId();
        }
        GoodsQuery goodsQuery=new GoodsQuery();
        goodsQuery.setBrandId(id.toString());
        Page<Goods> page = goodsService.searchGoods(pageable, goodsQuery);
        model.addAttribute("page", page);
        model.addAttribute("brands", brands);
        model.addAttribute("tags", tags);
        return "jewellery";
    }

    @RequestMapping("search")
    public String search(@PageableDefault(size = 9,sort = {"views"},direction = Sort.Direction.DESC) Pageable pageable,
                         String query,Model model){//前端传来query
        Page<Goods> page=goodsService.findAll(query,pageable);
        List<Brand> brands=brandService.findTop(5);
        List<Tag> tags=tagService.findTop(5);
        model.addAttribute("brands",brands);
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        return "jewellery";
    }
}
