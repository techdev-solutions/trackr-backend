package de.techdev.trackr.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import static java.util.Arrays.asList;

public class AuthorityMocks {

    /**
     * @return An authentication object for an employee with the id 100.
     */
    public static Authentication basicAuthentication() {
        return employeeAuthentication("some@techdev.de");
    }

    /**
     * An authentication of an employee with the id id.
     *
     * @return An authentication object for an employee.
     */
    public static Authentication employeeAuthentication(String username) {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return asList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
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
                return username;
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
                return asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
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
                return "moritz.schulze@techdev.de";
            }
        };
    }

    public static Authentication supervisorAuthentication() {
        return supervisorAuthentication("supervisor@techdev.de");
    }

    public static Authentication supervisorAuthentication(String username) {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return asList(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
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
                return username;
            }
        };
    }
}
