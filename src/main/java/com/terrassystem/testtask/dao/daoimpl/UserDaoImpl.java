package com.terrassystem.testtask.dao.daoimpl;

import com.terrassystem.testtask.dao.UserDao;
import com.terrassystem.testtask.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
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
}
