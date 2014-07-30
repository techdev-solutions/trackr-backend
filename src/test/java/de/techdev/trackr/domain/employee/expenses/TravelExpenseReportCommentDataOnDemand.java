package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportCommentDataOnDemand extends AbstractDataOnDemand<TravelExpenseReportComment> {

    @Autowired
    private TravelExpenseReportDataOnDemand travelExpenseReportDataOnDemand;

    @Override
    public TravelExpenseReportComment getNewTransientObject(int i) {
        TravelExpenseReportComment comment = new TravelExpenseReportComment();
        comment.setSubmissionDate(new Date());
        comment.setText("text_"+i);
        TravelExpenseReport report = travelExpenseReportDataOnDemand.getRandomObject();
        comment.setTravelExpenseReport(report);
        comment.setEmployee(report.getEmployee());
        return comment;
    }
}
