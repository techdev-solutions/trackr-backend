package de.techdev.trackr.security;

import de.techdev.trackr.domain.Credential;
import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.repository.CredentialRepository;
import de.techdev.trackr.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Load user details from the database.
 * <p>
 * If a user has a techdev email but is not in the database, create a locked account.
 *
 * @author Moritz Schulze
 */
@Slf4j
public class TrackrUserDetailsService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * If no user is found but the e-mail ends with techdev.de, a new but deactivated user is created in the database.
     *
     * @param token The open id token obtained from the login
     * @return User details if found.
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(noRollbackFor = UsernameNotFoundException.class)
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        Map<String, String> attributes = convertOpenIdAttributesToMap(token);
        String email = attributes.get("email");
        log.debug("Loading user {} from the database.", email);
        Credential credential = credentialRepository.findByEmail(email);
        if (credential == null) {
            if (email.endsWith("@techdev.de")) {
                log.debug("New techdev user with email {} found.", email);
                createDeactivatedEmployee(email, attributes.get("first"), attributes.get("last"));
                throw new UsernameNotFoundException("Your user has been created and is now waiting to be activated.");
            }
            throw new UsernameNotFoundException("User not found.");
        }
        if (!credential.getEnabled()) {
            //Unfortunately Spring Security ignores the enabled flag when using OpenID, so we have to do this in
            //this hacky way ourselves.
            log.debug("User {} is disabled, preventing log in.", email);
            throw new UsernameNotFoundException("User " + email + " is deactivated. Please wait for activation.");
        }
        return new User(credential.getEmail(), "", credential.getEnabled(), true, true, true, credential.getAuthorities());
    }

    private void createDeactivatedEmployee(String email, String first, String last) {
        Employee employee = new Employee();
        Credential credential = new Credential();
        employee.setFirstName(first);
        employee.setLastName(last);
        credential.setEmail(email);
        credential.setEnabled(false);
        credential.setEmployee(employee);
        employee.setCredential(credential);
        employeeRepository.saveAndFlush(employee);
    }

    /**
     * Conveniently transform the attributes to a map containing the first value of each attribute.
     *
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
