package com.lst.blog.service;

import com.lst.blog.po.Comment;

import java.security.PrivateKey;
import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

}
