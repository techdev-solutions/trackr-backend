package de.techdev.trackr.domain.employee.expenses.report.comment;

import de.techdev.test.rest.AbstractJsonGenerator;
import de.techdev.trackr.domain.employee.expenses.reports.comments.Comment;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentJsonGenerator extends AbstractJsonGenerator<Comment, CommentJsonGenerator> {

    private Long reportId;
    private Long employeeId;

    @Override
    protected String getJsonRepresentation(Comment object) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        jg.writeStartObject()
          .write("text", object.getText())
          .write("employee", "/employees/" + employeeId)
          .write("submissionDate", sdf.format(object.getSubmissionDate()))
          .write("travelExpenseReport", "/travelExpenseReports/" + reportId);

        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    public CommentJsonGenerator withReportId(Long reportId) {
        this.reportId = reportId;
        return this;
    }

    public CommentJsonGenerator withEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    @Override
    protected Comment getNewTransientObject(int i) {
        Comment comment = new Comment();
        comment.setSubmissionDate(new Date());
        comment.setText("text_" + i);
        return comment;
    }

    @Override
    protected CommentJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        reportId = null;
        employeeId = null;
    }
}
