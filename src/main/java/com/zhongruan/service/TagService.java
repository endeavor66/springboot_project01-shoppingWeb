package com.zhongruan.service;

import com.zhongruan.bean.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    /******** 露姐 ********/
    List<Tag> findSaleTop(int i);

    /******** 密姐 ********/
    List<Tag> findTop(int i);
    List<Tag> findAll();

    //明朗
    Page<Tag> findAll(Pageable pageable);

    Tag findById(Long id);

    void input(Tag tag);

    void deleteById(Long id);

    List<Tag> findByIds(String tagIds);
}
