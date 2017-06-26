package com.terrassystem.testtask.filters;

import com.terrassystem.testtask.security.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Павло on 21.06.2017.
 */

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authToken = null;
        if((authToken = httpServletRequest.getHeader("Authorization")) != null) {}
        else if ((authToken = httpServletRequest.getParameter("Authorization")) != null) {}
        else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if(authToken.startsWith("Basic ") && TokenUtil.validateJWT(authToken.substring("Basic ".length(), authToken.length()))) {
            authToken = authToken.substring("Basic ".length(), authToken.length());

            // obtain username from tocken
            String username = TokenUtil.getUserNameFromToken(authToken);

            //obtain user details
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(httpServletRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String checkCoockies(Cookie[] cookies) {
        for(Cookie c : cookies) {
            if(c.getName().equals("Authorization")) {
                return c.getValue();
            }
        }
        return null;
    }

}
