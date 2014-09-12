package de.techdev.trackr.domain.employee.login.support;

import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.AuthorityRepository;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Predicate;

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
        return getSupervisorEmailsArrayWithout(c -> true);
    }

    /**
     * @param withoutThese A predicate to filter out supervisors, e.g. the logged in principal.
     * @return Email addresses of all supervisors for whose credentials the predicate returns true.
     */
    public String[] getSupervisorEmailsArrayWithout(Predicate<Credential> withoutThese) {
        Authority supervisorRole = authorityRepository.findByAuthority("ROLE_SUPERVISOR");
        List<Credential> supervisors = credentialRepository.findByAuthorities(supervisorRole);
        return supervisors.stream().filter(withoutThese).map(Credential::getEmail).toArray(String[]::new);
    }
}
