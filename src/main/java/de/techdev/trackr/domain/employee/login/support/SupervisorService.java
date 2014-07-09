package de.techdev.trackr.domain.employee.login.support;

import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.AuthorityRepository;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Moritz Schulze
 */
public class SupervisorService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * @return Email addresses of all supervisors
     */
    public String[] getSupervisorEmailsAsArray() {
        Authority supervisorRole = authorityRepository.findByAuthority("ROLE_SUPERVISOR");
        List<Credential> supervisors = credentialRepository.findByAuthorities(supervisorRole);
        return supervisors.stream().map( Credential::getEmail ).toArray(String[]::new);
    }
}
