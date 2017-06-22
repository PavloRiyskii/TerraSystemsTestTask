package com.terrassystem.testtask.config;

import com.terrassystem.testtask.filters.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by Павло on 21.06.2017.
 */
//
@EnableWebMvc
@ComponentScan(value = "com.terrassystem.testtask")
@PropertySource(value = "classpath:databaseproperties.properties")
public class Config extends WebMvcConfigurerAdapter {

    /*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/users", "/roles", "/users/**", "/roles/**");
    }
    */

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
