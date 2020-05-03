package com.lst.blog.dao;

import com.lst.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);//自定义的函数只要满足命名规则，会自动实现

    //首页，按照分类所对应数目从大到小去前size个
    @Query("select t from t_type t")
    List<Type> findTop(Pageable pageable);
}
