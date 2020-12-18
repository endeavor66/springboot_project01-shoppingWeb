package com.zhongruan.service.impl;

import com.zhongruan.bean.Address;
import com.zhongruan.dao.AddressDao;
import com.zhongruan.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;


    @Override
    public List<Address> findByUid(Long uid) {
        return addressDao.findByUid(uid);
    }
}
