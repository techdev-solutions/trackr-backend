package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.project.ProjectDataOnDemand;
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

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Override
    public TravelExpenseReport getNewTransientObject(int i) {
        TravelExpenseReport travelExpenseReport = new TravelExpenseReport();
        travelExpenseReport.setEmployee(employeeDataOnDemand.getRandomObject());
        travelExpenseReport.setStatus(TravelExpenseReportStatus.PENDING);
        travelExpenseReport.setSubmissionDate(new Date());

        Project project = projectDataOnDemand.getRandomObject();
        travelExpenseReport.setDebitor(project.getCompany());
        if (i % 3 == 0) {
            travelExpenseReport.setProject(project);
        }
        return travelExpenseReport;
    }
}
