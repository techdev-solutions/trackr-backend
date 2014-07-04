package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportDataOnDemand extends AbstractDataOnDemand<TravelExpenseReport> {

    @Override
    protected int getExpectedElements() {
        return 1;
    }

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public TravelExpenseReport getNewTransientObject(int i) {
        TravelExpenseReport travelExpenseReport = new TravelExpenseReport();
        travelExpenseReport.setEmployee(employeeDataOnDemand.getRandomObject());
        travelExpenseReport.setStatus(TravelExpenseReportStatus.PENDING);
        travelExpenseReport.setSubmissionDate(new Date());
        return travelExpenseReport;
    }
}
