package com.terrassystem.testtask.dao.daoimpl;

import com.terrassystem.testtask.dao.RoleDao;
import com.terrassystem.testtask.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

/**
 * Created by Павло on 22.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.terrassystem.testtask.config.DBConfig.class,
        com.terrassystem.testtask.config.Config.class,
        com.terrassystem.testtask.config.SecurityConfig.class
})
@Transactional//(propagation = Propagation.REQUIRED)
@WebAppConfiguration
public class RoleDaoImplTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    @Rollback
    public void findByIdValid() {
        Role role = new Role();
        role.setName("some role");
        roleDao.save(role);
        assertEquals(roleDao.findById(role.getId()).getName(), role.getName());
    }

    @Test
    @Rollback
    public void findByIdUnexisting() {
        assertNull(roleDao.findById((long) -1));
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void fingByNullId() {
        this.roleDao.findById(null);
    }


    @Test
    @Rollback
    public void findAll() {
        Role role1 = new Role();
        Role role2 = new Role();
        role1.setName("role1");
        role2.setName("role2");
        this.roleDao.save(role1);
        this.roleDao.save(role2);
        assertEquals(this.roleDao.findAll().size(), 2);
    }

    @Test
    @Rollback
    public void findAllIfEmpty() {
        assertEquals(this.roleDao.findAll().size(), 0);
    }

    //Save method
    @Test
    @Rollback
    public void saveValid() {
        Role role = new Role();
        role.setName("some role");
        roleDao.save(role);
        assertEquals(roleDao.findById(role.getId()).getName(), role.getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void saveInvalid() {
        Role role = new Role();
        role.setName(null);
        roleDao.save(role);
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void saveNull() {
        Role role = null;
        roleDao.save(role);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void saveRoleNameNull() {
        Role r = new Role();
        r.setName(null);
        roleDao.save(r);
    }


    //Update  method
    @Test
    @Rollback
    public void updateValid() {
        Role role = new Role();
        role.setName("some role");
        roleDao.save(role);
        role.setName("new Name");
        roleDao.update(role);
        assertEquals(roleDao.findById(role.getId()).getName(), role.getName());
    }


    @Test(expected = InvalidDataAccessApiUsageException.class)
    @Rollback
    public void updateUnexisting() {
        Role role = new Role();
        role.setName("some role");
        this.roleDao.update(role);
    }

    //Delete methods
    @Test
    @Rollback
    public void delete() {
        Role role = new Role();
        role.setName("some role");
        roleDao.save(role);
        roleDao.delete(role);
        assertNull(roleDao.findById(role.getId()));
    }

    @Test
    @Rollback
    public void deleteUnexisting() {
        Role role = new Role();
        role.setName("rola name");
        this.roleDao.delete(role);
        //TODO - shoudnt work, should throw exception
    }

    @Test
    @Rollback
    public void findRoleByName() {
        Role r = new Role();
        r.setName("some name");
        this.roleDao.save(r);
        assertEquals(this.roleDao.findRoleByName(r.getName()).getId(), r.getId());
    }

}