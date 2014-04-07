package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportDataOnDemand extends AbstractDataOnDemand<TravelExpenseReport> {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public TravelExpenseReport getNewTransientObject(int i) {
        TravelExpenseReport travelExpenseReport = new TravelExpenseReport();
        travelExpenseReport.setEmployee(employeeDataOnDemand.getRandomObject());
        travelExpenseReport.setStatus(TravelExpenseReportStatus.PENDING);
        return travelExpenseReport;
    }
}
