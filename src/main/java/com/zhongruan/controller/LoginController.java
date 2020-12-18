package com.zhongruan.controller;

import com.zhongruan.bean.Brand;
import com.zhongruan.bean.Role;
import com.zhongruan.bean.Tag;
import com.zhongruan.bean.User;
import com.zhongruan.service.*;
import com.zhongruan.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("admin")
public class LoginController {

    private static int count=0;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private RoleService roleService;

    @GetMapping("toIndex")
    public String toIndex(Model model){
        Long userTotal=userService.count();
        int viewTotal=goodsService.getViewTotal();
        int saleTotal=goodsService.getSaleTotal();
        int incomeTotal=goodsService.getIncomeTotal();
        List<Brand> goods=brandService.findSaleTop(10);
        int brandSales=brandService.getTotalSale();
        model.addAttribute("userTotal",userTotal);
        model.addAttribute("viewTotal",viewTotal);
        model.addAttribute("saleTotal",saleTotal);
        model.addAttribute("incomeTotal",incomeTotal);
        model.addAttribute("brandSales",brandSales);
        model.addAttribute("goods",goods);
        return "admin/index";
    }


    @GetMapping("toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("login")
    public String login(String username, String password, HttpSession session, RedirectAttributes redirectAttributes){
        User user=userService.checkUser(username, MD5Util.code(password));
        if(user!=null){
            session.setAttribute("user",user);
            Set<Role> roles=user.getRoles();
            for(Role r:roles){
                if(r.getName().equals("admin")){
                    return "redirect:toIndex";
                }
            }
            return  "redirect:/toIndex?brandId=-1";
        }else{
            redirectAttributes.addFlashAttribute("message","The email does not exist or password error！");
            return "redirect:toLogin";
        }
    }

    @GetMapping("toRegister")
    public String toRegister(){
        return "register";
    }

    @PostMapping("register")
    public String register(User user) {
        user.setPassword(MD5Util.code(user.getPassword()));
        user.setPayPassword(MD5Util.code(user.getPayPassword()));
        user.setMoney(0.0F);
        Set<Role> userRole=new HashSet<>();
        userRole.add(roleService.findByName("user"));
        user.setRoles(userRole);
        userService.addUser(user,true);
        return "redirect:toLogin";
    }

    @RequestMapping("exit")
    public String exit(HttpSession session) {
        if(session.getAttribute("user")!=null){
            session.removeAttribute("user");
        }
        return "redirect:/toIndex?brandId=-1";
    }

    @RequestMapping("toRecharge")
    public String toRecharge(HttpSession session){
        if(session.getAttribute("user")!=null){
            return "recharge";
        }
        return "redirect:toLogin";
    }

    @RequestMapping("recharge")
    public String recharge(float money,HttpSession session) {
        count++;
        User user=(User)session.getAttribute("user");
        if(user!=null && count%2 !=0){
            user.setMoney(user.getMoney()+money);
            userService.addUser(user,false);
        }else if(user==null){
            return "redirect:toLogin";
        }
        return "rechargeSuccess";
    }

    @GetMapping("toResetPassword")
    public String toResetPassword(HttpSession session){
        if(session.getAttribute("user")!=null){
            return "resetPassword";
        }
        return "redirect:toLogin";
    }

    @PostMapping("resetPassword")
    public String resetPassword(String oldPassword,String newPassword,RedirectAttributes redirectAttributes,HttpSession session) {
        User user=(User)session.getAttribute("user");
        if(user!=null){
            if(user.getPassword().equals(MD5Util.code(oldPassword))){
                user.setPassword(MD5Util.code(newPassword));
                userService.addUser(user,false);
                return "redirect:/toIndex?brandId=-1";
            }else{
                redirectAttributes.addFlashAttribute("message","The old password is wrong! ");
                return "redirect:toResetPassword";
            }
        }else {
            return "redirect:toLogin";
        }
    }
}
