package com.zhongruan.dao;

import com.zhongruan.bean.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressDao extends JpaRepository<Address, Long> {

    @Query("select a from Address a where a.user.id = ?1 ")
    List<Address> findByUid(Long uid);
}
