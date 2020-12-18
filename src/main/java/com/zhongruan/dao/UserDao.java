package com.zhongruan.dao;

import com.zhongruan.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);

    //明朗
    @Query("select user from User user where user.username like ?1")
    Page<User> findAll(String s, Pageable pageable);
}
