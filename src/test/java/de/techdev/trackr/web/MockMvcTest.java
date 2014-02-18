package de.techdev.trackr.web;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.security.MethodSecurityConfiguration;
import de.techdev.trackr.security.SecurityConfiguration;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private WebApplicationContext webApplicationContext;

    @Before
    public final void setUpMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Get an admin authentication object.
     * Use with SecurityContextHolder.getContext().setAuthentication(adminAuthentication());
     * @return An admin authentication object, i.e. principal = "admin" and auhtorities = {"ROLE_ADMIN"}
     */
    protected Authentication adminAuthentication() {
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
                return null;
            }
        };
    }

    protected Authentication supervisorAuthentication() {
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
                return null;
            }
        };
    }
}
