package de.techdev.trackr.core.security;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.sql.DataSource;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Configuration
public class OAuth2ServerConfiguration {

    public static final String TRACKR_RESOURCE_ID = "techdev-services";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(TRACKR_RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(new RemoveSessionAndCookieForApiFilter(), SecurityContextPersistenceFilter.class);
            http.anonymous().authorities("ROLE_ANONYMOUS");
            http.requestMatchers().antMatchers("/api/**")
                    .and()
                    .authorizeRequests()
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

    @Configuration
    @EnableAuthorizationServer
    @PropertySource({"classpath:/META-INF/spring/database_${spring.profiles.active:dev}.properties"})
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Value("${tokenDatabase.driverClassName}")
        private String dbDriver;

        @Value("${tokenDatabase.url}")
        private String dbUrl;

        @Value("${tokenDatabase.username}")
        private String username;

        @Value("${tokenDatabase.password}")
        private String password;

        @Value("${tokenDatabase.jndiName}")
        private String jndiName;

        @Autowired
        private Environment env;

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory().withClient("trackr-page")
                .resourceIds(TRACKR_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "implicit") //TODO: what to set here?
                .authorities("ROLE_CLIENT")
                .scopes("read", "write");
        }

        @Bean
        @Profile({"qs", "prod"})
        public DataSource tokenDataSource() {
            if (asList(env.getActiveProfiles()).contains("prod")) {
                JndiDataSourceLookup lookup = new JndiDataSourceLookup();
                return lookup.getDataSource(jndiName);
            } else {
                BasicDataSource dataSource = new BasicDataSource();
                dataSource.setDriverClassName(dbDriver);
                dataSource.setUrl(dbUrl);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                return dataSource;
            }
        }

        @Bean
        public TokenStore tokenStore() {
            if(asList(env.getActiveProfiles()).contains("dev")) {
                return new InMemoryTokenStore();
            } else {
                return new JdbcTokenStore(tokenDataSource());
            }
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore());
        }
    }
}
