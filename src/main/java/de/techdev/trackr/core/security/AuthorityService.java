package de.techdev.trackr.core.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Access GrantedAuthorities for Employees and emails by GrantedAuthorities
 */
public interface AuthorityService {

    /**
     * Get all authorities for an employee, identified by the email address.
     */
    Collection<GrantedAuthority> getByEmployeeMail(String email);

    /**
     * Get all email addresses from employees who have the given authority.
     */
    Collection<String> getEmployeeEmailsByAuthority(String authority);

}
