package com.zhongruan.service.impl;

import com.zhongruan.bean.User;
import com.zhongruan.dao.UserDao;
import com.zhongruan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findById(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    @Override
    public User checkUser(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }

    @Override
    public void addUser(User user, boolean create) {
        if(create){
            user.setCreateTime(new Date());
        }
        userDao.save(user);
    }

    //明朗
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Page<User> findAll(String username, Pageable pageable) {
        return  userDao.findAll("%"+username+"%",pageable);
    }

    @Override
    public Long count() {
        return userDao.count();
    }
}
