package com.terrassystem.testtask.services.servicesimpl;

import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.services.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by Павло on 23.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.terrassystem.testtask.config.DBConfig.class,
        com.terrassystem.testtask.config.Config.class,
        com.terrassystem.testtask.config.SecurityConfig.class
})
@Transactional//(propagation = Propagation.REQUIRED)
@WebAppConfiguration
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Test
    @Rollback
    public void addRole() {
        Role role = new Role();
        role.setName("role");
        roleService.addRole(role);
        assertEquals(roleService.getRoleById((role.getId())).getName(), role.getName());
    }

    @Test
    @Rollback
    public void addExistingRole() {
        Role role = new Role();
        Role existingRole = new Role();
        role.setName("name");
        existingRole.setName("name");
        this.roleService.addRole(role);
        this.roleService.addRole(existingRole);
        assertEquals(this.roleService.getAllRoles().size(), 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void addInvalidRole() {
        Role role = new Role();
        role.setName(null);
        this.roleService.addRole(role);
    }

    @Test(expected = NullPointerException.class)
    @Rollback
    public void addNullRole() {
        Role r = null;
        this.roleService.addRole(r);
    }

    //update
    @Test
    @Rollback
    public void updateRole() {
        Role role = new Role();
        role.setName("role");
        this.roleService.addRole(role);
        role.setName("name");
        this.roleService.updateRole(role);
        assertEquals(this.roleService.getRoleById(role.getId()).getName(), role.getName());
    }

    //delete role
    @Test
    @Rollback
    public void deleteRole() {
        Role role = new Role();
        role.setName("role");
        this.roleService.addRole(role);
        this.roleService.deleteRole(role);
        assertNull(this.roleService.getRoleById(role.getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void deleteUnexistingRole() {
        Role role = new Role();
        role.setId((long)-1);
        this.roleService.deleteRole(role);
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void deleteNullRole() {
        this.roleService.deleteRole(null);
    }
    //get by id
    @Test
    @Rollback
    public void getRoleById() {
        Role role = new Role();
        role.setName("some role");
        roleService.addRole(role);
        assertEquals(roleService.getRoleById((role.getId())).getName(), role.getName());
    }

    @Test
    @Rollback
    public void getRoleByUnexstingId() {
        assertNull(this.roleService.getRoleById((long)-1));
    }


    //get all roles
    @Test
    @Rollback
    public void getAllRoles() {
        Role role1 = new Role();
        Role role2 = new Role();
        role1.setName("role1");
        role2.setName("role2");
        this.roleService.addRole(role1);
        this.roleService.addRole(role2);
        assertEquals(this.roleService.getAllRoles().size(), 2);
    }
}