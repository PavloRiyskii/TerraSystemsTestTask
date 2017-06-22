package com.terrassystem.testtask.config;

import com.terrassystem.testtask.filters.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by Павло on 20.06.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean(name = "authenticationFilter")
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }


    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(authenticationFilter());
        filterRegBean.addUrlPatterns("/users", "/roles", "/users/**", "/roles/**");
        filterRegBean.setName("authenticationFilter");
        filterRegBean.setEnabled(true);
        filterRegBean.setAsyncSupported(true);
        return filterRegBean;
    }

    @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class)
               .csrf()
                   .disable()
               .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                   .authorizeRequests()
                   .antMatchers("/users").permitAll()
                    .antMatchers("/roles").permitAll()
               .and()
          ;
    }
}
