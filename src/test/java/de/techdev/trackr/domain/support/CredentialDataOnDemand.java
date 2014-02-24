package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.Credential;
import de.techdev.trackr.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Component
public class CredentialDataOnDemand extends AbstractDataOnDemand<Credential> {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Override
    public Credential getNewTransientObject(int i) {
        Credential credential = new Credential();
        credential.setEnabled(false);
        credential.setEmail("email_" + i + "@techdev.de");
        Employee employee = employeeDataOnDemand.getNewTransientObject(i);
        credential.setEmployee(employee);
        Authority authority = authorityDataOnDemand.getRandomObject();
        credential.setAuthorities(asList(authority));
        return credential;
    }
}
