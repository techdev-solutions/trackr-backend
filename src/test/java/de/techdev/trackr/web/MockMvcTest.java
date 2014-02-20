package de.techdev.trackr.web;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.security.MethodSecurityConfiguration;
import de.techdev.trackr.security.SecurityConfiguration;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * A base class for tests that access the web mvc resources.
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {MethodSecurityConfiguration.class, SecurityConfiguration.class})
public abstract class MockMvcTest extends IntegrationTest {

    protected final String standardContentType = "application/hal+json";

    protected MockMvc mockMvc;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public final void setUpMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).addFilter(filterChainProxy).build();
    }

    private MockHttpSession buildSession(Authentication authentication) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, new MockSecurityContext(authentication));
        return session;
    }

    protected MockHttpSession basicSession() {
        return buildSession(basicAuthentication());
    }

    protected MockHttpSession supervisorSession() {
        return buildSession(supervisorAuthentication());
    }

    protected MockHttpSession adminSession() {
        return buildSession(adminAuthentication());
    }

    private Authentication basicAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return asList(new Authority("ROLE_EMPLOYEE"));
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
                return ((Principal) () -> "user@techdev.de");
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
                return "user@techdev.de";
            }
        };
    }

    /**
     * Get an admin authentication object.
     * Use with SecurityContextHolder.getContext().setAuthentication(adminAuthentication());
     * @return An admin authentication object, i.e. principal = "admin" and auhtorities = {"ROLE_ADMIN"}
     */
    private Authentication adminAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return asList(new Authority("ROLE_ADMIN"));
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
                return ((Principal)() -> "admin");
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
                return "admin";
            }
        };
    }

    private Authentication supervisorAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return asList(new Authority("ROLE_SUPERVISOR"));
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
                return ((Principal)() -> "supervisor");
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
                return "supervisor";
            }
        };
    }

    /**
     * Mock for the security context to provide our own authentications in a MockHttpSession.
     *
     * See <a href="http://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication">stackoverflow</a> for this idea.
     */
    protected class MockSecurityContext implements SecurityContext {

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }
}
