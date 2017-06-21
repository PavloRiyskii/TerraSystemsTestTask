package com.terrassystem.testtask.config;

import com.terrassystem.testtask.filters.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/users", "/roles", "/users/**", "/roles/**");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
 /*
    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new AuthenticationFilter());
        filterRegBean.addUrlPatterns("/users", "/roles", "/users/**", "/roles/**");
        filterRegBean.setName("Authentication filter");
        filterRegBean.setEnabled(Boolean.TRUE);
        filterRegBean.setAsyncSupported(Boolean.TRUE);
        return filterRegBean;
    }*/
}
