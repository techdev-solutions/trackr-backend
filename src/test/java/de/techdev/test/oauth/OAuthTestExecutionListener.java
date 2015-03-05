package de.techdev.test.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.echocat.jomon.runtime.CollectionUtils.asSet;

/**
 * Test execution listener that adds an OAuth token into the store before each method. The value of the token can be set with the
 * {@link OAuthRequest} annotation on each test method.
 */
@Slf4j
public class OAuthTestExecutionListener extends AbstractTestExecutionListener {

    public static final String OAUTH_TOKEN_VALUE = "token";
    private final OAuth2AccessToken accessToken;
    private final OAuth2Request clientAuthentication;

    public OAuthTestExecutionListener() {
        accessToken = new DefaultOAuth2AccessToken(OAUTH_TOKEN_VALUE);
        // TODO no magic constants! These should probably come from the OAuthResourceServer Configuration!
        clientAuthentication = new OAuth2Request(new HashMap<>(), "trackr-page", null, true, asSet("read", "write"), asSet("techdev-services"), null, null, null);
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        OAuthRequest annotation = AnnotationUtils.getAnnotation(testContext.getTestMethod(), OAuthRequest.class);

        if (annotation == null) {
            annotation = AnnotationUtils.getAnnotation(testContext.getTestClass(), OAuthRequest.class);
        }

        if(annotation == null) {
            log.warn("No OAuthToken annotation for {} in class {}, did you forget it?", testContext.getTestMethod().getName(), testContext.getTestClass().getName());
        } else {
            insertOauthTokenIntoStore(testContext, annotation);
        }
    }

    private void insertOauthTokenIntoStore(TestContext testContext, @Nonnull OAuthRequest token) {
        TokenStore tokenStore = testContext.getApplicationContext().getBean(TokenStore.class);
        tokenStore.storeAccessToken(accessToken, new OAuth2Authentication(clientAuthentication, getAuthenticationFromAnnotation(token)));
    }

    private Authentication getAuthenticationFromAnnotation(OAuthRequest token) {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return asList(token.value())
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return new User(getName(), "", getAuthorities());
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return token.username();
            }
        };
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        TokenStore tokenStore = testContext.getApplicationContext().getBean(TokenStore.class);
        tokenStore.removeAccessToken(accessToken);
    }

}
