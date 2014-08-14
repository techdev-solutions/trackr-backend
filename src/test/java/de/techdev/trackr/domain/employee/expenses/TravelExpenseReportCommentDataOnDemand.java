package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.expenses.reports.comments.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportCommentDataOnDemand extends AbstractDataOnDemand<Comment> {

    @Autowired
    private TravelExpenseReportDataOnDemand travelExpenseReportDataOnDemand;

    @Override
    public Comment getNewTransientObject(int i) {
        Comment comment = new Comment();
        comment.setSubmissionDate(new Date());
        comment.setText("text_"+i);
        Report report = travelExpenseReportDataOnDemand.getRandomObject();
        comment.setTravelExpenseReport(report);
        comment.setEmployee(report.getEmployee());
        return comment;
    }
}
