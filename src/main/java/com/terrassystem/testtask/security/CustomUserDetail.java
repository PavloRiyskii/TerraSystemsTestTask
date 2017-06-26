package com.terrassystem.testtask.security;

import com.terrassystem.testtask.entity.Role;
import com.terrassystem.testtask.entity.User;
import io.jsonwebtoken.lang.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Павло on 24.06.2017.
 */
public class CustomUserDetail extends User implements UserDetails {


    public CustomUserDetail(User u) {
        super();
        this.setId(u.getId());
        this.setActive(u.isActive());
        this.setRoles(u.getRoles());
        this.setName(u.getName());
        this.setPassword(u.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ListIterator<Role> roles = this.getRoles().listIterator();
        List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
        while(roles.hasNext()) {
            authorities.add(new SimpleGrantedAuthority(roles.next().getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
    }
}
