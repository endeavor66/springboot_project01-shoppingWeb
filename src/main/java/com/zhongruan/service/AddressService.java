package com.zhongruan.service;

import com.zhongruan.bean.Address;

import java.util.List;

public interface AddressService {


    List<Address> findByUid(Long uid);
}
