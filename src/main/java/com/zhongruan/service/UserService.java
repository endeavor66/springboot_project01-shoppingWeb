package com.zhongruan.service;

import com.zhongruan.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    User findById(Long id);

    void update(User user);

    User findByUsernameAndPassword(String username, String password);

    //明朗
    User checkUser(String username,String password);

    void addUser(User user, boolean create);

    Page<User> findAll(Pageable pageable);

    void deleteById(Long id);

    Page<User> findAll(String username, Pageable pageable);

    Long count();
}
