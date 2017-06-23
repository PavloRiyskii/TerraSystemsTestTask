package com.terrassystem.testtask.dao.daoimpl;

import com.terrassystem.testtask.dao.UserDao;
import com.terrassystem.testtask.entity.User;
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
 * Created by Павло on 22.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.terrassystem.testtask.config.DBConfig.class,
        com.terrassystem.testtask.config.Config.class,
        com.terrassystem.testtask.config.SecurityConfig.class
})
@Transactional//(propagation = Propagation.REQUIRED)
@WebAppConfiguration
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    // find by id
    @Test
    @Rollback
    public void findById() throws Exception {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setActive(false);
        this.userDao.save(user);
        assertEquals(this.userDao.findById(user.getId()).getName(), user.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void findByNullId() {
        this.userDao.findById(null);
    }

    @Test
    @Rollback
    public void findByIdUnexistingId() {
        assertNull(this.userDao.findById((long)-1));
    }
    // find all
    @Test
    @Rollback
    public void findAll() throws Exception {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setActive(false);
        User user1 = new User();
        user1.setName("name");
        user1.setPassword("password");
        user1.setActive(false);
        this.userDao.save(user);
        this.userDao.save(user1);
        assertEquals(this.userDao.findAll().size(), 2);
    }

    @Test
    @Rollback
    public void findAllWhenEmpty() {
        assertTrue(this.userDao.findAll().isEmpty());
    }

    //save
    @Test
    @Rollback
    public void save() throws Exception {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        this.userDao.save(user);
        assertEquals(this.userDao.findById(user.getId()).getName(), user.getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void saveUnvalidUser() {
        User user = new User();
        user.setName(null);
        user.setPassword(null);
        user.setActive(false);
        this.userDao.save(user);
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void saveNullUser() {
        this.userDao.save(null);
    }

    //update
    @Test
    @Rollback
    public void update() throws Exception {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        userDao.save(user);
        user.setName("new user name");
        userDao.update(user);
        assertEquals(this.userDao.findById(user.getId()).getName(), user.getName());
    }

    @Test
    @Rollback
    public void updateInvalid() {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        userDao.save(user);
        user.setName(null);
        userDao.update(user);
    }

    @Test
    @Rollback
    public void delete()  {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        userDao.save(user);
        userDao.delete(user);
        assertNull(this.userDao.findById(user.getId()));
    }

    //getUserByUsernameAndPassword
    @Test
    @Rollback
    public void getUserByUsernameAndPassword() throws Exception {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        userDao.save(user);
        assertEquals(this.userDao.getUserByUsernameAndPassword(user.getName(), user.getPassword()).getId(), user.getId());
    /*
    * org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet
    */
    }

}