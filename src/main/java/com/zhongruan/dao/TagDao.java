package com.zhongruan.dao;

import com.zhongruan.bean.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagDao extends JpaRepository<Tag,Long>, JpaSpecificationExecutor<Tag> {

    //密姐
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
