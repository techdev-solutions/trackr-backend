package de.techdev.trackr.domain.employee.expenses.report.comment;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import de.techdev.trackr.domain.employee.expenses.reports.comments.Comment;

import org.junit.Test;

import javax.json.stream.JsonGenerator;

import java.io.StringWriter;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.isCreated;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isForbidden;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isMethodNotAllowed;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommentResourceTest extends AbstractDomainResourceTest<Comment> {

    @Override
    protected String getResourceName() {
        return "travelExpenseReportComments";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void oneNotExported() throws Exception {
        assertThat(one(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void createAllowedForOwningEmployee() throws Exception {
        assertThat(create(comment -> employeeSession(comment.getEmployee().getId())), isCreated());
    }

    @Test
    public void createAllowedForSupervisor() throws Exception {
        assertThat(create(supervisorSession()), isCreated());
    }

    @Test
    public void updateForbidden() throws Exception {
        assertThat(update(adminSession()), isForbidden());
    }

    @Test
    public void deleteNotExported() throws Exception {
        assertThat(remove(adminSession()), isMethodNotAllowed());
    }

    @Override
    protected String getJsonRepresentation(Comment comment) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
                .write("text", comment.getText())
                .write("employee", "/employees/" + comment.getEmployee().getId())
                .write("submissionDate", ISO_INSTANT.format(comment.getSubmissionDate()))
                .write("travelExpenseReport", "/travelExpenseReports/" + comment.getTravelExpenseReport().getId());


        if (comment.getId() != null) {
            jg.write("id", comment.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}