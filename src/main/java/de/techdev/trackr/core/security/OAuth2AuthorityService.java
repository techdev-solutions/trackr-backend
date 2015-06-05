package de.techdev.trackr.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Collection;

@Service
@Profile("oauth")
public class OAuth2AuthorityService implements AuthorityService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OAuth2AuthorityService(@Qualifier("oauthDataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<String> getEmployeeEmailsByAuthority(String authority) {
        return jdbcTemplate.queryForList("SELECT username FROM authorities WHERE authority = ?", String.class, authority);
    }

}