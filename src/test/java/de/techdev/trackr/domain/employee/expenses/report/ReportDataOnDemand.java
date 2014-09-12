package de.techdev.trackr.domain.employee.expenses.report;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.project.ProjectDataOnDemand;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

/**
 * @author Moritz Schulze
 */
public class ReportDataOnDemand extends AbstractDataOnDemand<Report> {

    @Override
    protected int getExpectedElements() {
        return 1;
    }

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Override
    public Report getNewTransientObject(int i) {
        Report travelExpenseReport = new Report();
        travelExpenseReport.setEmployee(employeeDataOnDemand.getRandomObject());
        travelExpenseReport.setStatus(Report.Status.PENDING);
        travelExpenseReport.setSubmissionDate( Instant.now());

        Project project = projectDataOnDemand.getRandomObject();
        travelExpenseReport.setDebitor(project.getCompany());
        if (i % 3 == 0) {
            travelExpenseReport.setProject(project);
        }
        return travelExpenseReport;
    }
}
