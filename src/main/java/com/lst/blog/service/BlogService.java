package com.lst.blog.service;

import com.lst.blog.po.Blog;
import com.lst.blog.vo.BlogQuery;
import org.jboss.logging.BasicLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable); //查询所有含某tag的博客

    Blog getAndConvert(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();//归档

    Long countBlog();

    Page<Blog> listBlog(String query,Pageable pageable);
}
