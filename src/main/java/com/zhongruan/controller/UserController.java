package com.zhongruan.controller;

import com.zhongruan.bean.Goods;
import com.zhongruan.bean.User;
import com.zhongruan.service.GoodsService;
import com.zhongruan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

//    /**
//     * 跳转到主页
//     * @return
//     */
//    @RequestMapping("toIndex")
//    public String toIndex(){
//        return "index";
//    }
//
//    /**
//     * 跳转到登录页面
//     * @return
//     */
//    @RequestMapping("toLogin")
//    public String toLogin(){
//        return "login";
//    }
//
//    /**
//     * 登录校验，成功则返回主页
//     * @param username
//     * @param password
//     * @param session
//     * @return
//     */
//    @RequestMapping("login")
//    public String login(String username, String password, HttpSession session){
//        User user = userService.findByUsernameAndPassword(username, password);
//        if(user == null){
//            System.out.println("登陆失败");
//        }else{
//            session.setAttribute("user", user);
//        }
//        return "index";
//    }

    @RequestMapping
    public String find(@PageableDefault(size=5,sort={"createTime"},direction = Sort.Direction.DESC)Pageable pageable, @RequestParam(defaultValue = "0") int count, Model model){
        Page<User> page=userService.findAll(pageable);
        model.addAttribute("page",page);
        if (count == 1) {
            return "admin/users::usersList";
        }
        else{
            return "admin/users";
        }
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        userService.deleteById(id);
        return "redirect:/admin/user";
    }

    @RequestMapping("search")
    public String search(@PageableDefault(size=5,sort={"createTime"},direction = Sort.Direction.DESC)Pageable pageable, String username, @RequestParam(defaultValue = "0") int count, Model model){
        Page<User> page=userService.findAll(username,pageable);
        model.addAttribute("searchName",username);
        model.addAttribute("page",page);
        if (count == 1) {
            return "admin/users::usersList";
        }
        else{
            return "admin/users";
        }
    }


}
