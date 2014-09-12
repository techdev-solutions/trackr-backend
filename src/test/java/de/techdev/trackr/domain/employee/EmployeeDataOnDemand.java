package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.AuthorityDataOnDemand;
import de.techdev.trackr.domain.employee.login.Credential;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Moritz Schulze
 */
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
        employee.setJoinDate(LocalDate.now());
        employee.setFederalState(FederalState.BERLIN);
        employee.setVacationEntitlement((float) i);
        Credential credential = new Credential();
        credential.setEmployee(employee);
        credential.setEmail("email_" + i + "@techdev.de");
        credential.setEnabled(false);
        credential.setLocale("en");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityDataOnDemand.getRandomObject());
        credential.setAuthorities(authorities);
        employee.setCredential(credential);
        return employee;
    }
}
