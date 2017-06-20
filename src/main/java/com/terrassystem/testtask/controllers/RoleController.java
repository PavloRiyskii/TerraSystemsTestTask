package com.terrassystem.testtask.controllers;

import com.google.gson.Gson;
import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Павло on 20.06.2017.
 */
@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getRoles() {
        List<Role> roles = this.roleService.getAllRoles();
        String jsonResponse = new Gson().toJson(roles);
        return new ResponseEntity<String>(jsonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getRole(@PathVariable Long id) {
        Role role = this.roleService.getRoleById(id);
        String jsonResponse = new Gson().toJson(role);
        return new ResponseEntity<String>(jsonResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> createNewRole(@RequestBody Role role) {
        if(role.getId() != null) {
            new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        this.roleService.addRole(role);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateRole(@RequestBody Role role){
        if(role.getId() == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        this.roleService.updateRole(role);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRole(@RequestBody Role role){
        if(role.getId() == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        this.roleService.deleteRole(role);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
