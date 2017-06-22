package com.terrassystem.testtask.init;

import com.terrassystem.testtask.config.Config;
import com.terrassystem.testtask.config.DBConfig;
import com.terrassystem.testtask.config.SecurityConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Павло on 21.06.2017.
 */
public class ApplicationInit implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
        registerDispatcherServlet(servletContext);
    }

    private void registerDispatcherServlet(ServletContext servletContext) {
        WebApplicationContext applicationContext = this.getContext( Config.class, SecurityConfig.class, DBConfig.class);
        //servletContext.addFilter("AuthenticationFilter", new AuthenticationFilter());
        DispatcherServlet dispatcherServlet= new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }

    public WebApplicationContext getContext(final Class<?>... annotatedClasses) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(annotatedClasses);
        return context;
    }
}
