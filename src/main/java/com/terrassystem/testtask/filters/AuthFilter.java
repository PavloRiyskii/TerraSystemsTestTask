package com.terrassystem.testtask.filters;

import com.terrassystem.testtask.security.TokenUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Павло on 21.06.2017.
 */
public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String jwt = request.getHeader("Authorization");
        if(!TokenUtil.validateJWT(jwt)) {
            response.sendError(400, "Invalid token");
        }
        filterChain.doFilter(servletRequest, servletResponse);
        //TODO - add to headers "Content-Type":"application/json" and "Accept": "application/json"
    }

    public void destroy() {}
}
