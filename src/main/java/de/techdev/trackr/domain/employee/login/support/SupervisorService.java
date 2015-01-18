package de.techdev.trackr.domain.employee.login.support;

import de.techdev.trackr.core.security.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.function.Predicate;

public class SupervisorService {

    @Autowired
    private AuthorityService authorityService;

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
    public String[] getSupervisorEmailsArrayWithout(Predicate<String> withoutThese) {
        Collection<String> supervisor = authorityService.getEmployeeEmailsByAuthority("ROLE_SUPERVISOR");
        return supervisor.stream().filter(withoutThese).toArray(String[]::new);
    }
}
