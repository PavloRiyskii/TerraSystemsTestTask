package com.terrassystem.testtask.filters;

import com.terrassystem.testtask.security.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Павло on 21.06.2017.
 */

@Component
@Order(1)
@WebFilter({"/users", "/roles", "/users/*", "/roles/*"})
public class AuthenticationFilter extends GenericFilterBean {

    final static Logger logger = Logger.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authToken = null;
        if((authToken = httpRequest.getHeader("Authorization")) != null) {}
        else if ((authToken = httpRequest.getParameter("Authorization")) != null) {}
        else {
            httpResponse.sendError(401, "Invalid token");
            return;
        }

        if(authToken.startsWith("Basic ") && TokenUtil.validateJWT(authToken.substring("Basic ".length(), authToken.length()))) {
            authToken = authToken.substring("Basic ".length(), authToken.length());
            String username = TokenUtil.getUserNameFromToken(authToken);
            String password = TokenUtil.getPasswordFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                httpResponse.sendError(401, "Invalid token");
                return;
            }
        } else {
            httpResponse.sendError(401, "Invalid token");
            return;
        }

        chain.doFilter(request, response);
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
