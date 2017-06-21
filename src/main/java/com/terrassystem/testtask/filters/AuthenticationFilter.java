package com.terrassystem.testtask.filters;

import com.terrassystem.testtask.security.TokenUtil;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Павло on 21.06.2017.
 */
/*
@Order(1)
@WebFilter({"/users", "/roles", "/users/*", "/roles/*"})
*/
public class AuthenticationFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authToken = httpRequest.getHeader("Authorization");
        if(authToken != null && authToken.startsWith("Basic ")) {
            String username = TokenUtil.getUserNameFromToken(authToken);
            String password = TokenUtil.getPasswordFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && TokenUtil.validateJWT(authToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                httpResponse.sendError(401, "Invalid token");
            }
        } else {
            httpResponse.sendError(401, "Invalid token");
        }
        chain.doFilter(request, response);
    }

    public void destroy() {}

}
