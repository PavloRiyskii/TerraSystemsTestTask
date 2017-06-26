package com.terrassystem.testtask.controllers;

import com.google.gson.Gson;
import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.entity.User;
import com.terrassystem.testtask.services.RoleService;
import com.terrassystem.testtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Павло on 19.06.2017.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@PathVariable("id") long id) {
        User user = this.userService.getUserById(id);
        String json = new Gson().toJson(user);
        return new ResponseEntity<String>(json, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        String json = new Gson().toJson(users);
        return new ResponseEntity<String>(json, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        if(user.getId() == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        this.userService.addUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method= RequestMethod.PATCH, consumes = {"application/json"})
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        if(user.getId() == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        user.setRoles(this.userService.getUserById(user.getId()).getRoles());
        this.userService.updateUser(user);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        if(user.getId() == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        this.userService.deleteUser(user);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PreFilter(value = "")
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.GET)
    public ResponseEntity<String> getUserRoles(@PathVariable("id") Long id) {
        List<Role> roles = this.userService.getUserById(id).getRoles();
        String json = new Gson().toJson(roles);
        return new ResponseEntity<String>(json, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.POST)
    public ResponseEntity<Void> addUserRole(@RequestParam("roleId") Long roleId, @PathVariable("id") Long userId) {
        User user = this.userService.getUserById(userId);
        Role role = this.roleService.getRoleById(roleId);
        List<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        this.userService.updateUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value =  "/{id}/roles", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeUserRole(@RequestParam("roleId") Long roleId, @PathVariable("id") Long userId) {
        User user = this.userService.getUserById(userId);
        Role role = this.roleService.getRoleById(roleId);
        List<Role> userRoles = user.getRoles();
        userRoles.remove(role);
        user.setRoles(userRoles);
        this.userService.updateUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
