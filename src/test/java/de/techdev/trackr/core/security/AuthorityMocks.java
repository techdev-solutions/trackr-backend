package de.techdev.trackr.core.security;

import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.TrackrUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Locale;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
public class AuthorityMocks {

    /**
     * @return An authentication object for an employee with the id 100.
     */
    public static Authentication basicAuthentication() {
        return employeeAuthentication(100L);
    }

    /**
     * An authentication of an employee with the id id.
     *
     * @param id The desired id of the employee.
     * @return An authentication object for an employee.
     */
    public static Authentication employeeAuthentication(Long id) {
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
                return new TrackrUser("user@techdev.de", true, getAuthorities(), id, Locale.GERMAN);
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
     *
     * @return An admin authentication object, i.e. principal = "admin@techdev.de" and auhtorities = {"ROLE_ADMIN"}
     */
    public static Authentication adminAuthentication() {
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
                return new TrackrUser(getName(), true, getAuthorities(), 0L, Locale.GERMAN);
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
                return "admin@techdev.de";
            }
        };
    }

    public static Authentication supervisorAuthentication() {
        return supervisorAuthentication(5000L);
    }

    public static Authentication supervisorAuthentication(Long id) {
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
                return new TrackrUser(getName(), true, getAuthorities(), id, Locale.GERMAN);
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
                return "supervisor@techdev.de";
            }
        };
    }
}
