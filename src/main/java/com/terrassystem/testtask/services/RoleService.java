package com.terrassystem.testtask.services;

import com.terrassystem.testtask.entity.Role;

import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
public interface RoleService {
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(Role role);
    Role getRoleById(long id);
    List<Role> getAllRoles();
}
