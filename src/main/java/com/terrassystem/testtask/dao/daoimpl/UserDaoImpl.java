package com.terrassystem.testtask.dao.daoimpl;

import com.terrassystem.testtask.dao.UserDao;
import com.terrassystem.testtask.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", username));
        criteria.add(Restrictions.eq("password", password));
        List<User> result = criteria.list();
        return result.isEmpty() ?  null : result.get(0);
    }

    @Override
    public User getUserByUsername(String username) {
        Criteria criteria  = this.sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", username));
        List<User> users = criteria.list();
        return users.isEmpty() ? null : users.get(0);
    }
}
