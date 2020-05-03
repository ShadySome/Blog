package com.lst.blog.service;

import com.lst.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(long id);

    Tag updateTag(long id, Tag tag);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    void deleteTag(long id);

    Tag getTagByName(String name);

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);
}
