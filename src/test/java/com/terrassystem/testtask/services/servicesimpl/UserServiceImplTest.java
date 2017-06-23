package com.terrassystem.testtask.services.servicesimpl;

import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.entity.User;
import com.terrassystem.testtask.services.RoleService;
import com.terrassystem.testtask.services.UserService;
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
 * Created by Павло on 23.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.terrassystem.testtask.config.DBConfig.class,
        com.terrassystem.testtask.config.Config.class,
        com.terrassystem.testtask.config.SecurityConfig.class
})
@Transactional//(propagation = Propagation.REQUIRED)
@WebAppConfiguration
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    @Rollback
    public void addUser() {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        this.userService.addUser(user);
        assertEquals(this.userService.getUserById(user.getId()).getName(), user.getName());
    }

    @Test
    @Rollback
    public void addExistingUser() {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        this.userService.addUser(user);
        User user1 = new User();
        user1.setName("username");
        user1.setPassword("password");
        user1.setActive(false);
        this.userService.addUser(user1);
        assertEquals(this.userService.getAllUsers().size(), 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void addUserInvalidField() {
        User user = new User();
        user.setName(null);
        user.setPassword("password");
        user.setActive(false);
        this.userService.addUser(user);

    }

    @Test(expected = NullPointerException.class)
    @Rollback
    public void addNullUser() {
        this.userService.addUser(null);
    }

    @Test
    @Rollback
    public void updateUser() {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        this.userService.addUser(user);
        user.setName("new user name");
        this.userService.updateUser(user);
        assertEquals(this.userService.getUserById(user.getId()).getName(), user.getName());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    @Rollback
    public void updateUnexistingUser() {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        this.userService.updateUser(user);
    }


    @Test
    @Rollback
    public void deleteUser() {
        User user = new User();
        user.setName("username");
        user.setPassword("password");
        user.setActive(false);
        this.userService.addUser(user);
        this.userService.deleteUser(user);
        assertNull(this.userService.getUserById(user.getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void deleteUnexistingUser() {
        User user = new User();
        user.setId((long) -1);
        this.userService.deleteUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void deleteNullUser() {
        this.userService.deleteUser(null);
    }

    @Test
    @Rollback
    public void getAllUsers() {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setActive(false);
        User user1 = new User();
        user1.setName("name1");
        user1.setPassword("password");
        user1.setActive(false);
        this.userService.addUser(user);
        this.userService.addUser(user1);
        assertEquals(this.userService.getAllUsers().size(), 2);
    }

    @Test
    @Rollback
    public void getUserById() {
        User user = new User();
        user.setName("name");
        user.setPassword("pass");
        user.setActive(false);
        this.userService.addUser(user);
        assertEquals(this.userService.getUserById(user.getId()).getName(), user.getName());
    }

    @Test
    @Rollback
    public void getUserByUnexistingId() {
        assertNull(this.userService.getUserById((long) -1));
    }

    @Test
    @Rollback
    public void addRole() {
        User user = new User();
        user.setName("name");
        user.setPassword("pass");
        user.setActive(false);
        this.userService.addUser(user);

        Role r = new Role();
        r.setName("role name");
        this.roleService.addRole(r);

        this.userService.addRole(user, r);

        assertEquals(this.userService.getUserById(user.getId()).getRoles().size(), 1);
    }

    @Test
    @Rollback
    public void addUnexistingRole() {
        User user = new User();
        user.setName("name");
        user.setPassword("pass");
        user.setActive(false);
        this.userService.addUser(user);

        Role role = new Role();
        role.setId((long) -1);
        role.setName("name");

        this.userService.addRole(user, role);
        assertEquals(this.userService.getUserById(user.getId()).getRoles().size(), 0);
    }

    @Test
    @Rollback
    public void removeRole() {
        User user = new User();
        user.setName("name");
        user.setPassword("pass");
        user.setActive(false);
        this.userService.addUser(user);

        Role r = new Role();
        r.setName("role name");
        this.roleService.addRole(r);

        this.userService.addRole(user, r);
        this.userService.removeRole(user, r);

        assertTrue(this.userService.getUserById(user.getId()).getRoles().isEmpty());
    }

    @Test
    @Rollback
    public void getByUsernameAndPassword() {
        User user = new User();
        user.setName("name");
        user.setPassword("pass");
        userService.addUser(user);
        assertEquals(this.userService.getByUsernameAndPassword(user.getName(), user.getPassword()).getId(), user.getId());
    }

    @Test
    @Rollback
    public void getByUsernameAndPasswordUnexist() {
        assertNull(this.userService.getByUsernameAndPassword("1", "2"));
    }

}