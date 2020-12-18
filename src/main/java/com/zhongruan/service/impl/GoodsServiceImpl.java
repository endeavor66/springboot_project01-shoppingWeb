package com.zhongruan.service.impl;

import com.zhongruan.bean.Brand;
import com.zhongruan.bean.Goods;
import com.zhongruan.bean.GoodsQuery;
import com.zhongruan.bean.Tag;
import com.zhongruan.dao.GoodsDao;
import com.zhongruan.service.GoodsService;
import com.zhongruan.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public Goods findById(Long id) {
        return goodsDao.getOne(id);
    }

    @Override
    public void update(Goods goods) {
        goodsDao.save(goods);
    }

    /*********  明朗  ********/
    @Override
    public int getSaleTotal() {
        return goodsDao.countOfSale();
    }

    @Override
    public int getViewTotal() {
        return goodsDao.countOfView();
    }

    @Override
    public int getIncomeTotal() {
        return goodsDao.getIncome();
    }

    /*********  露姐  ********/
    /**
     * 商品详情页：展示该商品所属品牌的销量最佳的几款商品
     * @param i
     * @param brandId
     * @return
     */
    @Override
    public List<Goods> findSaleTopByBrandId(int i, Long brandId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "sale");
        Pageable pageable = PageRequest.of(0,i,sort);
        return goodsDao.findSalesTop(brandId,pageable);
    }

    /*********  密姐  ********/
    @Override
    public Page<Goods> findAll(Pageable pageable) {
        return goodsDao.findAll(pageable);
    }

    @Override
    public Page<Goods> searchGoods(Pageable pageable, Long id) {
        return goodsDao.findAll(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join=root.join("tagList");//单个查询条件
                return criteriaBuilder.equal(join.get("id"),id);
            }
        }, pageable);
    }

    @Override
    public Page<Goods> searchGoods(Pageable pageable, GoodsQuery newsQuery) {
        Specification s=new Specification<Goods>(){
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();//放查询条件
                if(!StringUtils.isEmpty(newsQuery.getTitle())){//判断是否为空
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+newsQuery.getTitle()+"%"));//模糊查询
                }
                if(!StringUtils.isEmpty(newsQuery.getBrandId())){
                    predicates.add(criteriaBuilder.equal(root.<Brand>get("brand").get("id"),newsQuery.getBrandId()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));//集合转数组,拼接查询条件
                return null;
            }
        };
        Page<Goods> page=goodsDao.findAll(s,pageable);
        return page;
    }

    @Override
    public Page<Goods> findTop(Pageable pageable, String s){
        Sort sort = Sort.by(Sort.Direction.DESC, s);
        List<Goods> list=goodsDao.findAll(sort);
        // 当前页第一条数据在List中的位置
        int start = (int)pageable.getOffset();
        // 当前页最后一条数据在List中的位置
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : ( start + pageable.getPageSize());
        // 配置分页数据
        Page<Goods> page = new PageImpl<Goods>(list.subList(start, end), pageable, list.size());
        return page;
               /* findAll(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return query.orderBy(criteriaBuilder.asc(root.get("price")));
            }
        }, pageable);*/
    }
    @Override
    public Page<Goods> findAll(String query, Pageable pageable) {
        return goodsDao.findByQuery("%"+query+"%",pageable);
    }

    //春玲
    @Override
    public void deleteById(Long id) {
        goodsDao.deleteById(id);
    }


    @Override
    public void add(Goods goods) {
        Date d=new Date();
        String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        String updateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        if(goods.getId()==null){
            goods.setCreateTime(createTime);
            goods.setUpdateTime(updateTime);
            goodsDao.save(goods);
        }else {
            Goods g = goodsDao.getOne(goods.getId());
            BeanUtils.copyProperties(goods,g, MyBeanUtils.getNullPropertyNames(goods));
            g.setUpdateTime(updateTime);
            goodsDao.save(g);
        }
    }



    @Override
    public String findTagIds(List<Tag> tags) {
        //[3,]  [4, ]     3,4
        StringBuffer stringBuffer=new StringBuffer();
        if(!tags.isEmpty()){
            boolean flag=false;
            for(Tag tag:tags){
                if(flag){
                    stringBuffer.append(",");
                    stringBuffer.append(tag.getId());
                }else {
                    stringBuffer.append(tag.getId());
                    flag=true;
                }
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public Page<Goods> searchNews(Pageable pageable, GoodsQuery goodsQuery) {
        Specification s=new Specification<Goods>(){
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if(!StringUtils.isEmpty(goodsQuery.getGoodsName())){
                    predicates.add(criteriaBuilder.like(root.<String>get("goodsName"),"%"+goodsQuery.getGoodsName()+"%"));
                }
                if(!StringUtils.isEmpty(goodsQuery.getBrandId())){
                    predicates.add(criteriaBuilder.equal(root.<Brand>get("brand").get("id"),goodsQuery.getBrandId()));
                }
                if(!StringUtils.isEmpty(goodsQuery.getUpdateYear())){
                    predicates.add(criteriaBuilder.like(root.<String>get("updateTime"), goodsQuery.getUpdateYear()+"%"));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        };
        Page<Goods> page=goodsDao.findAll(s,pageable);
        return page;
    }

    @Override
    public List<String> findGroupYear() {
        return goodsDao.findGroupYear();
    }
}
