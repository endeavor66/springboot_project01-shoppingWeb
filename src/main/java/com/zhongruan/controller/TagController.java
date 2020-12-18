package com.zhongruan.controller;

import com.zhongruan.bean.Tag;
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


@Controller
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping
    public String find(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
    Model model){
        Page<Tag> page=tagService.findAll(pageable);
        model.addAttribute("page",page);
        return "admin/tag";
    }

    @RequestMapping("toInput/{id}")
    public String toInput(@PathVariable Long id,Model model){
        if(id==-1){
            model.addAttribute("tag",new Tag());
        }else {
            Tag tag=tagService.findById(id);
            model.addAttribute("tag",tag);
        }
        return "admin/tag-input";

    }

    @RequestMapping("input")
    public String input(Tag tag){
        tagService.input(tag);
        return "redirect:/admin/tags";
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        tagService.deleteById(id);
        return "redirect:/admin/tags";
    }


}
