package com.terrassystem.testtask.services;

import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.entity.User;

import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
public interface UserService {
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserById(long id);
    List<User> getAllUsers();

    void addRole(User user, Role role);
    void removeRole(User user, Role role);

    User getByUsernameAndPassword(String username, String password);
}
