package com.lst.blog.service;

import com.lst.blog.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
