package com.lst.blog.service;

import com.lst.blog.po.Tag;
import com.lst.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TypeService {

    Type saveType(Type type);//增

    Type getType(long id); //查

    Page<Type> ListType(Pageable pageable);//Pageable 是Spring Data库中定义的一个接口，用于构造翻页查询，是所有分页相关信息的一个抽象，通过该接口，我们可以得到和分页相关所有信息（例如pageNumber、pageSize等），这样，Jpa就能够通过pageable参数来得到一个带分页信息的Sql语句。

    Type updateType(long id, Type type); //改

    void deleteType(long id); //删

    Type getTypeByName(String name);

    List<Type> ListType();

    List<Type> listTypeTop(Integer size);

}
