package de.techdev.trackr.domain;

import de.techdev.trackr.domain.DataConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * @author Alexander Hanschke
 */
@Configuration
public class JndiDataConfig implements DataConfig {

    @Value("${tokenDatabase.jndiName}")
    private String jndiName;

    @Override
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource(jndiName);
    }
}
