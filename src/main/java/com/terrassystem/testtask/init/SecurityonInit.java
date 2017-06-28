package com.terrassystem.testtask.init;

import com.terrassystem.testtask.config.Config;
import com.terrassystem.testtask.config.DBConfig;
import com.terrassystem.testtask.config.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Павло on 21.06.2017.
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
