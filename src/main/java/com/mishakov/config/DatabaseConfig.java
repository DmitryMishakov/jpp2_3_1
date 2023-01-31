//package com.mishakov.config;
//
//
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@ComponentScan("com.mishakov")
//@PropertySource("classpath:db.properties")
//public class DatabaseConfig {
//
//    private Environment env;
//
//    public DatabaseConfig(Environment env) {
//        this.env = env;
//    }
//
//
//
//@Bean
//    public DataSource dataSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setUrl(env.getRequiredProperty("db.url"));
//        ds.setDriverClassName(env.getRequiredProperty("db.driver_class"));
//        ds.setUsername(env.getRequiredProperty("db.username"));
//        ds.setPassword(env.getRequiredProperty("db.password"));
//
//        ds.setInitialSize(Integer.parseInt(env.getRequiredProperty("db.initialSize")));
//        ds.setMinIdle(Integer.parseInt(env.getRequiredProperty("db.minIdle")));
//        ds.setMaxIdle(Integer.parseInt(env.getRequiredProperty("db.maxIdle")));
//        ds.setTimeBetweenEvictionRunsMillis(Long
//                .parseLong(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
//        ds.setMinEvictableIdleTimeMillis(Long
//                .parseLong(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
//        ds.setTestOnBorrow(Boolean.parseBoolean(env.getRequiredProperty("db.testOnBorrow")));
//        ds.setValidationQuery(env.getRequiredProperty("db.validationQuery"));
//
//        return ds;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaProperties(getHibernateProperties());
//        return em;
//    }
//
//    public Properties getHibernateProperties() {
//        try {
//            Properties properties = new Properties();
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
//            properties.load(inputStream);
//            return properties;
//        } catch (IOException e) {
//            throw new IllegalArgumentException("can't find hibernate properties", e);
//        }
//    }
//
//    @Bean
//    public PlatformTransactionManager platformTransactionManager() {
//        JpaTransactionManager manager = new JpaTransactionManager();
//        manager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return manager;
//    }
//}
