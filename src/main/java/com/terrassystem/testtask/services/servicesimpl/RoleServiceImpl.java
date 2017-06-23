package com.terrassystem.testtask.services.servicesimpl;

import com.terrassystem.testtask.dao.RoleDao;
import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Transactional
    public void addRole(Role role) {
        if(this.roleDao.findRoleByName(role.getName()) == null) {
            this.roleDao.save(role);
        }
    }

    @Transactional
    public void updateRole(Role role) {
        this.roleDao.update(role);
    }

    @Transactional
    public void deleteRole(Role role) {
        this.roleDao.delete(role);
    }

    @Transactional
    public Role getRoleById(long id) {
        return this.roleDao.findById(id);
    }

    @Transactional
    public List<Role> getAllRoles() {
        return this.roleDao.findAll();
    }
}
