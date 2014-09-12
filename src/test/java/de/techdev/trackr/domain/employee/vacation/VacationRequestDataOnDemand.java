package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
public class VacationRequestDataOnDemand extends AbstractDataOnDemand<VacationRequest> {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public VacationRequest getNewTransientObject(int i) {
        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        vacationRequest.setEmployee(employeeDataOnDemand.getRandomObject());
        vacationRequest.setStartDate(LocalDate.now());
        vacationRequest.setEndDate( LocalDate.now());
        return vacationRequest;
    }

}
