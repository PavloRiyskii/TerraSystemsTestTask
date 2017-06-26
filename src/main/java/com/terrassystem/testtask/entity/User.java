package com.terrassystem.testtask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Павло on 19.06.2017.
 */
@Entity
@Table(name = "users")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name ="password", nullable = false)
    private String password;
    // TODO - prevent parsing to json password

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Role> getRoles() {
        if(this.roles == null) {
            return new LinkedList<Role>();
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this){
            return true;
        }
        if (object == null || object.getClass() != this.getClass()){
            return false;
        }

        User user = (User) object;
        if(user.getId() == this.id && user.getName().equals(this.name) && user.getPassword().equals(this.getPassword()) &&
                user.getRoles().hashCode() == this.roles.hashCode()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() + this.name.hashCode() + this.getPassword().hashCode() + this.roles.hashCode();
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
}