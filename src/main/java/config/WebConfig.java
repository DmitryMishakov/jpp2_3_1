package config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("java")
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    private final Environment environment;

    private Logger logger = LoggerFactory.getLogger(WebConfig.class.getName());

    public WebConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("database.driver_class"));
        dataSource.setUrl(environment.getRequiredProperty("database.connection.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.connection.username"));
        dataSource.setPassword(environment.getRequiredProperty("database.connection.password"));

        dataSource.setInitialSize(Integer.parseInt(environment.getRequiredProperty("database.initialSize")));
        dataSource.setMinIdle(Integer.parseInt(environment.getRequiredProperty("database.minIdle")));
        dataSource.setMaxIdle(Integer.parseInt(environment.getRequiredProperty("database.maxIdle")));
        dataSource
                .setTimeBetweenEvictionRunsMillis(Long
                        .parseLong(environment.getRequiredProperty("database.timeBetweenEvictionRunsMillis")));
        dataSource
                .setMinEvictableIdleTimeMillis(Long
                        .parseLong(environment.getRequiredProperty("database.minEvictableIdleTimeMillis")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(environment.getRequiredProperty("database.testOnBorrow")));
        dataSource.setValidationQuery(environment.getRequiredProperty("database.validationQuery"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());

        return manager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean
                .setPackagesToScan(environment.getRequiredProperty("database.entity.package"));

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }

    private Properties getHibernateProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("hibernate.properties");
            properties.load(inputStream);
            return properties;
        } catch (IOException ex) {
            logger.error("can't find property file");
            throw new IllegalArgumentException(ex);
        }
    }

}
