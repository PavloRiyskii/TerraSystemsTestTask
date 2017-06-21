package com.terrassystem.testtask.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by Павло on 21.06.2017.
 */
@Configuration
@EnableTransactionManagement
public class DBConfig {

    //JDBC properties
    @Value(value = "${jdbc.driverClassName}")
    public String driverClassName;

    @Value("${jdbc.databaseurl}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    //HibernateProperty
    @Value(value = "${hibernate.dialect}")
    private String dialect;

    @Value(value = "${hibernate.connection.pool_size}")
    private String poolSize;

    @Value(value = "${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;
    @Bean
    public BasicDataSource getBasicDataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(driverClassName);
        source.setUrl(url);
        source.setUsername(username);
        source.setPassword(password);
        return source;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getBasicDataSource());
        sessionFactory.setPackagesToScan(new String[] {
                "com.terrassystem.testtask.entity."
        });
        sessionFactory.setHibernateProperties(getHibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactory().getObject());
        return manager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.connection.pool_size", poolSize);
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        return properties;
    }
}
