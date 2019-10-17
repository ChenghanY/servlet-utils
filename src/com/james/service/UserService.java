package com.james.service;

import com.james.dao.UserDAO;
import com.james.domain.User;

/**
 * @Author james
 * @Description
 * @Date 2019/10/15
 */
public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User doLogin(String username, String password) {
        return userDAO.findUser(username, password);
    }

}
