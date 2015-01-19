package de.techdev.trackr.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * @author Alexander Hanschke
 */
@Configuration
@Profile("prod")
public class JndiDataConfig implements DataConfig {

    @Value("${tokenDatabase.jndiName}")
    private String jndiName;

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource(jndiName);
    }
}
