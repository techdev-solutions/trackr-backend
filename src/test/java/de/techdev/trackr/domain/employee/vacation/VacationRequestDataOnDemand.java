package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class VacationRequestDataOnDemand extends AbstractDataOnDemand<VacationRequest> {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public VacationRequest getNewTransientObject(int i) {
        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequest.setEmployee(employeeDataOnDemand.getRandomObject());
        vacationRequest.setStartDate(new Date());
        vacationRequest.setEndDate(new Date());
        return vacationRequest;
    }

}
