package de.techdev.trackr.domain.employee.expenses.report.comment;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.expenses.report.ReportDataOnDemand;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.expenses.reports.comments.Comment;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
/**
 * @author Moritz Schulze
 */
public class CommentDataOnDemand extends AbstractDataOnDemand<Comment> {

    @Autowired
    private ReportDataOnDemand travelExpenseReportDataOnDemand;

    @Override
    public Comment getNewTransientObject(int i) {
        Comment comment = new Comment();
        comment.setSubmissionDate(Instant.now());
        comment.setText("text_"+i);
        Report report = travelExpenseReportDataOnDemand.getRandomObject();
        comment.setTravelExpenseReport(report);
        comment.setEmployee(report.getEmployee());
        return comment;
    }
}
