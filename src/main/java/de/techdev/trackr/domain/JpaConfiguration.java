package de.techdev.trackr.domain;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.Repository;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Moritz Schulze
 * @author Alexander Hanschke
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"de.techdev.trackr"},
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {Repository.class})
        })

@PropertySources({
        @PropertySource("classpath:/META-INF/spring/database_${spring.profiles.active:dev}.properties"),
        @PropertySource(value = "${trackr.externalconfig:file:/etc/trackr.properties}", ignoreResourceNotFound = true)
})
public class JpaConfiguration {

    @Autowired
    private DataConfig dataConfig;

    @Value("${database.hibernateDialect}")
    private String hibernateDialect;

    @Value("${database.hbm2ddlAuto}")
    private String hbm2ddlAuto;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        return dataConfig.dataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPersistenceProviderClass(HibernatePersistence.class);
        emfb.setPackagesToScan("de.techdev.trackr");
        emfb.setJpaProperties(hibernateProperties());
        return emfb;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

}

