package de.techdev.trackr.core.security;

import java.util.Collection;

/**
 * Access GrantedAuthorities for Employees and emails by GrantedAuthorities
 */
public interface AuthorityService {

    /**
     * Get all email addresses from employees who have the given authority.
     */
    Collection<String> getEmployeeEmailsByAuthority(String authority);

}
