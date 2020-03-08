package com.nuaa.shawn.demo.service;

import com.nuaa.shawn.demo.dao.UserDAO;
import com.nuaa.shawn.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}