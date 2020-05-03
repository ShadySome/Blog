package com.lst.blog.service;

import com.lst.blog.L_NotFoundException;
import com.lst.blog.dao.TagRepository;
import com.lst.blog.dao.TypeRepository;
import com.lst.blog.po.Tag;
import com.lst.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> ListType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(long id, Type type) {
        Type t = typeRepository.getOne(id);
        if(t == null){
            throw new L_NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);//把type赋值到t
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Type> ListType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }
}
