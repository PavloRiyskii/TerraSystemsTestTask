package com.terrassystem.testtask.config;

import com.terrassystem.testtask.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.management.MXBean;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Павло on 20.06.2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService);
    }

    @Bean(name = "authenticationFilter")
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .exceptionHandling().
                    authenticationEntryPoint(authenticationEntryPoint)
                    .and()
               .sessionManagement()
                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                   .antMatchers("/auth").permitAll()
                    .antMatchers(HttpMethod.PUT, "users").permitAll()
                   .anyRequest().authenticated().and()
               .csrf()
                    .disable();

       httpSecurity
               .addFilterBefore(authenticationFilter(),UsernamePasswordAuthenticationFilter.class);

       httpSecurity
               .headers()
               .cacheControl();
    }
}
