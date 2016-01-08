package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.company.Address;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

public class Projections {

    @Projection(types = Employee.class, name = "withAddress")
    public interface WithAddressProjection {

        Long getId();

        Integer getVersion();

        String getFirstName();

        String getLastName();

        String getEmail();

        String getPhoneNumber();

        String getTitle();

        BigDecimal getSalary();

        BigDecimal getHourlyCostRate();

        Date getJoinDate();

        Date getLeaveDate();

        FederalState getFederalState();

        Float getVacationEntitlement();

        Address getAddress();

        boolean isDeleted();

    }

}
