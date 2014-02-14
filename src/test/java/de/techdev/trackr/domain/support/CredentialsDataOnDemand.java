package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.Credentials;
import de.techdev.trackr.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Component
public class CredentialsDataOnDemand extends AbstractDataOnDemand<Credentials> {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    public Credentials getRandomCredentials() {
        init();
        Credentials obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return repository.findOne(id);
    }

    @Override
    public Credentials getNewTransientObject(int i) {
        Credentials credentials = new Credentials();
        credentials.setEnabled(false);
        credentials.setEmail("email_" + i + "@techdev.de");
        Employee employee = employeeDataOnDemand.getNewTransientObject(i);
        credentials.setEmployee(employee);
        Authority authority = authorityDataOnDemand.getRandomAuthority();
        credentials.setAuthorities(asList(authority));
        return credentials;
    }
}
