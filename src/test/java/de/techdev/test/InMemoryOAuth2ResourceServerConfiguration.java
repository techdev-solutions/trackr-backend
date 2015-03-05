package de.techdev.test;

import de.techdev.trackr.core.security.OAuth2ResourceServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.sql.DataSource;

/**
 * Uses the real OAuth2 resource server configuration but with an in memory token store so we can easily modifiy it in tests.
 */
@Configuration
@Profile("test-oauth")
public class InMemoryOAuth2ResourceServerConfiguration extends OAuth2ResourceServerConfiguration {

    @Override
    public DataSource oauthDataSource() {
        return null;
    }

    @Override
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
}
