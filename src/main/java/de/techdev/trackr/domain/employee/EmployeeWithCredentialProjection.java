package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.login.Credential;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Projection(types = Employee.class, name = "withCredential")
public interface EmployeeWithCredentialProjection {
    Long getId();

    Integer getVersion();

    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    String getTitle();

    BigDecimal getSalary();

    BigDecimal getHourlyCostRate();

    Date getJoinDate();

    Date getLeaveDate();

    FederalState getFederalState();

    Float getVacationEntitlement();

    Credential getCredential();
}
