package com.terrassystem.testtask.dao.daoimpl;

import com.terrassystem.testtask.dao.UserDao;
import com.terrassystem.testtask.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public User findById(Long id) {
        return (User) this.sessionFactory.getCurrentSession().get(User.class, id);
    }

    public List<User> findAll() {
        return this.sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }

    public void save(User user) {
        this.sessionFactory.getCurrentSession().save(user);
    }

    public void update(User user) {
        this.sessionFactory.getCurrentSession().update(user);
    }

    public void delete(User user) {
        this.sessionFactory.getCurrentSession().delete(user);
        this.sessionFactory.getCurrentSession().flush();
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        return (User) this.sessionFactory.getCurrentSession().createQuery("FROM User WHERE name = " + username +
                " and password = " + password).list().get(0);
    }
}
