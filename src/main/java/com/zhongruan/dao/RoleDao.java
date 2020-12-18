package com.zhongruan.dao;

import com.zhongruan.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
