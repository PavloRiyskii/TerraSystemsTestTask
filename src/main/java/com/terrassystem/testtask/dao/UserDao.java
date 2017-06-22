package com.terrassystem.testtask.dao;

import com.terrassystem.testtask.entity.User;

/**
 * Created by Павло on 19.06.2017.
 */
public interface UserDao extends GenericDao<User, Long> {
    User getUserByUsernameAndPassword(String username, String password);
}
