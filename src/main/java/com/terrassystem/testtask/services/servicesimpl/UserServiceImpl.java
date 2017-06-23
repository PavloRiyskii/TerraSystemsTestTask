package com.terrassystem.testtask.services.servicesimpl;

import com.terrassystem.testtask.dao.RoleDao;
import com.terrassystem.testtask.dao.UserDao;
import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.entity.User;
import com.terrassystem.testtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Павло on 19.06.2017.
 */
@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private UserDao userDao;

   @Autowired
   private RoleDao roleDao;

   @Transactional
   public void addUser(User user) {
       if(this.userDao.getUserByUsernameAndPassword(user.getName(), user.getPassword()) == null) {
           this.userDao.save(user);
       }
    }

    @Transactional
    public void updateUser(User user) {
       if(user.getName() != null && user.getPassword() != null) {
           this.userDao.update(user);
       }
    }

    @Transactional
    public void deleteUser(User user) {
        this.userDao.delete(user);
    }

    @Transactional
    public User getUserById(long id) {
        return this.userDao.findById(id);
    }

    @Transactional
    public List<User> getAllUsers() {
        return this.userDao.findAll();
    }

    @Transactional
    public void addRole(User user, Role role) {
        Set<Role> roles = user.getRoles();

        if(this.roleDao.findById(role.getId()) != null) {
            roles.add(role);
            user.setRoles(roles);
            this.userDao.update(user);
        }
    }

    @Transactional
    public void removeRole(User user, Role role){
        Set<Role> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
        this.userDao.update(user);
    }

    @Transactional
    public User getByUsernameAndPassword(String username, String password) {
        return this.userDao.getUserByUsernameAndPassword(username, password);
    }
}
