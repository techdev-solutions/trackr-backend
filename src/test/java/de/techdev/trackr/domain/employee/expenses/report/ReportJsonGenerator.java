package de.techdev.trackr.domain.employee.expenses.report;

import de.techdev.test.rest.AbstractJsonGenerator;
import de.techdev.trackr.domain.employee.expenses.reports.Report;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportJsonGenerator extends AbstractJsonGenerator<Report, ReportJsonGenerator> {

    private Long employeeId;
    private Long debitorId;
    private Long projectId;

    public ReportJsonGenerator withEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public ReportJsonGenerator withDebitorId(Long debitorId) {
        this.debitorId = debitorId;
        return this;
    }

    public ReportJsonGenerator withProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(Report report) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("version", report.getVersion())
          .write("status", report.getStatus().toString());

        if (employeeId != null) {
            jg.write("employee", "/api/employees/" + employeeId);
        }

        if (debitorId != null) {
            jg.write("debitor", "/api/companies/" + debitorId);
        }

        if (projectId != null) {
            jg.write("project", "/api/projects/" + projectId);
        }

        if (report.getSubmissionDate() != null) {
            jg.write("submissionDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(report.getSubmissionDate()));
        }

        if (report.getId() != null) {
            jg.write("id", report.getId());
        }

        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected Report getNewTransientObject(int i) {
        Report report = new Report();
        report.setVersion(0);
        report.setStatus(Report.Status.PENDING);
        report.setSubmissionDate(new Date());

        return report;
    }

    @Override
    protected ReportJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        employeeId = null;
        debitorId = null;
        projectId = null;
    }
}
