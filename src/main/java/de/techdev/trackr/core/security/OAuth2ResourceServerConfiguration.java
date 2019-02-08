package de.techdev.trackr.core.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Collections;

@Profile("oauth")
@EnableWebSecurity
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String TRACKR_RESOURCE_ID = "techdev-services";

    @Bean
    @Qualifier("oauthDataSource")
    @ConfigurationProperties(prefix = "trackr.database.oauth")
    public DataSource oauthDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(oauthDataSource());
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(TRACKR_RESOURCE_ID).tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        User anonymousUser =
                new User("anonymous", "EMPTY", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));

        http
                .anonymous()
                .principal(anonymousUser)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // since this is just a REST API we don't need a state.
                .and().requestMatchers().antMatchers("/**")
                .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')");

        http.headers().addHeaderWriter((request, response) -> {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            if (request.getMethod().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });
    }
}
