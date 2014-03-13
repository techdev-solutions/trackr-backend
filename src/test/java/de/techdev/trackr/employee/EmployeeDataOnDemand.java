package de.techdev.trackr.employee;

import de.techdev.trackr.domain.Credential;
import de.techdev.trackr.domain.FederalState;
import de.techdev.trackr.domain.support.AbstractDataOnDemand;
import de.techdev.trackr.employee.login.Authority;
import de.techdev.trackr.employee.login.AuthorityDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Component
public class EmployeeDataOnDemand extends AbstractDataOnDemand<Employee> {

    @Override
    protected int getExpectedElements() {
        return 7;
    }

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Override
    public Employee getNewTransientObject(int i) {
        Employee employee = new Employee();
        employee.setFirstName("firstName_" + i);
        employee.setLastName("lastName_" + i);
        employee.setHourlyCostRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        employee.setPhoneNumber("phoneNumber_" + i);
        employee.setTitle("title_" + i);
        employee.setJoinDate(new Date());
        employee.setFederalState(FederalState.BERLIN);
        employee.setVacationEntitlement((float) i);
        Credential credential = new Credential();
        credential.setEmployee(employee);
        credential.setEmail("email_" + i + "@techdev.de");
        credential.setEnabled(false);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityDataOnDemand.getRandomObject());
        credential.setAuthorities(authorities);
        employee.setCredential(credential);
        return employee;
    }
}
