package de.techdev.trackr.security;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Load user details from the database
 * If a user has a techdev email but is not in the database, create a locked account.
 * @author Moritz Schulze
 */
public class TrackrUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Loads user details from an OpenID token.
     * If a user is found by the e-mail, UserDetails are created and returned.
     * If no user is found but the e-mail ends with techdev.de, a new but deactivated user is created in the database.
     * @param token The open id token obtained from the login
     * @return User details if found.
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(noRollbackFor = UsernameNotFoundException.class)
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        Map<String, String> attributes = convertOpenIdAttributesToMap(token);
        String email = attributes.get("email");
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            if(email.endsWith("@techdev.de")) {
                createDeactivatedEmployee(email, attributes.get("first"), attributes.get("last"));
                throw new UsernameNotFoundException("Your user has been created and is now waiting to be activated.");
            }
            throw new UsernameNotFoundException("User not found.");
        }
        if(!employee.isEnabled()) {
            //Unfortunately Spring Security ignores the enabled flag when using OpenID, so we have to do this in
            //this hacky way ourselves.
            throw new UsernameNotFoundException("User " + email + " is deactivated. Please wait for activation.");
        }
        return new User(employee.getEmail(), "", employee.isEnabled(), true, true, true, employee.getAuthorities());
    }

    private void createDeactivatedEmployee(String email, String first, String last) {
        Employee employee = new Employee();
        employee.setFirstName(first);
        employee.setLastName(last);
        employee.setEmail(email);
        employee.setEnabled(false);
        employeeRepository.saveAndFlush(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("Login by username not allowed");
    }

    /**
     * Conveniently transform the attributes to a map containing the first value of each attribute.
     * @param token The OpenID authentication token to transform
     * @return A map containing a name->value mapping
     */
    private Map<String, String> convertOpenIdAttributesToMap(OpenIDAuthenticationToken token) {
        Map<String, String> attributeMap = new HashMap<>();
        for (OpenIDAttribute attribute : token.getAttributes()) {
            attributeMap.put(attribute.getName(), attribute.getValues().get(0));
        }
        return attributeMap;
    }
}
