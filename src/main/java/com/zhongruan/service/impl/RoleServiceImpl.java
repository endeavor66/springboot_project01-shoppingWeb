package com.zhongruan.service.impl;

import com.zhongruan.bean.Role;
import com.zhongruan.dao.RoleDao;
import com.zhongruan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    public Role findByName(String name){
        return roleDao.findByName(name);
    }
}
