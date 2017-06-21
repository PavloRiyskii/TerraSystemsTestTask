package com.terrassystem.testtask.dao.daoimpl;

import com.terrassystem.testtask.dao.RoleDao;
import com.terrassystem.testtask.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Role findById(Long id) {
        return (Role) this.sessionFactory.getCurrentSession().get(Role.class, id);
    }

    public List<Role> findAll() {
        return this.sessionFactory.getCurrentSession().createCriteria(Role.class).list();
    }

    public void save(Role role) {
        this.sessionFactory.getCurrentSession().save(role);
    }

    public void update(Role role) {
        this.sessionFactory.getCurrentSession().update(role);
    }

    public void delete(Role role) {
        this.sessionFactory.getCurrentSession().delete(role);
        this.sessionFactory.getCurrentSession().flush();
    }
}
