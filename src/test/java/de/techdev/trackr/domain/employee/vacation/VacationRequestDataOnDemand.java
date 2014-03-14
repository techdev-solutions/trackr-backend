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
    EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public VacationRequest getNewTransientObject(int i) {
        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setApproved(i % 2 != 0);
        vacationRequest.setEmployee(employeeDataOnDemand.getRandomObject());
        vacationRequest.setStartDate(new Date());
        vacationRequest.setEndDate(new Date());
        if(vacationRequest.getApproved()) {
            vacationRequest.setApprovalDate(new Date());
            vacationRequest.setApprover(employeeDataOnDemand.getRandomObject());
        }
        return vacationRequest;
    }

}
