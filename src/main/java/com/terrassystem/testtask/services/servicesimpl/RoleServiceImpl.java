package com.terrassystem.testtask.services.servicesimpl;

import com.terrassystem.testtask.dao.RoleDao;
import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public void addRole(Role role) {
        this.roleDao.save(role);
    }

    public void updateRole(Role role) {
        this.roleDao.update(role);
    }

    public void deleteRole(Role role) {
        this.roleDao.delete(role);
    }

    public Role getRoleById(long id) {
        return this.roleDao.findById(id);
    }

    public List<Role> getAllRoles() {
        return this.roleDao.findAll();
    }
}
