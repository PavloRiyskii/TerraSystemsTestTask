package com.terrassystem.testtask.filters;

import com.terrassystem.testtask.entity.User;
import com.terrassystem.testtask.security.TokenUtil;
import com.terrassystem.testtask.services.UserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Павло on 21.06.2017.
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    //TODO - chenge expiration time to new per request if token valid
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String authToken = httpServletRequest.getHeader("Authorization");
        if(authToken != null && authToken.startsWith("Basic ") && TokenUtil.validateJWT(authToken.substring("Basic ".length(), authToken.length()))){
            authToken = authToken.substring("Basic ".length(), authToken.length());
            String username = TokenUtil.getUserNameFromToken(authToken);
            String password = TokenUtil.getPasswordFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                httpServletResponse.sendError(401, "Invalid token");
                return false;
            }
        } else {
            httpServletResponse.sendError(401, "Invalid token");
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
