package com.terrassystem.testtask.services.servicesimpl;

import com.terrassystem.testtask.dao.UserDao;
import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.entity.User;
import com.terrassystem.testtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * Created by Павло on 19.06.2017.
 */
public class UserServiceImpl implements UserService {

   @Autowired
   private UserDao userDao;

    public void addUser(User user) {
        this.userDao.save(user);
    }

    public void updateUser(User user) {
        this.userDao.update(user);
    }

    public void deleteUser(User user) {
        this.userDao.delete(user);
    }

    public User getUserById(long id) {
        return this.userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return this.userDao.findAll();
    }

    public void addRole(User user, Role role) {
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        this.userDao.update(user);
    }

    public void removeRole(User user, Role role){
        Set<Role> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
        this.userDao.update(user);
    }
}
