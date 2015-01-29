package de.techdev.trackr.domain.employee.vacation;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VacationRequestJsonGenerator extends AbstractJsonGenerator<VacationRequest, VacationRequestJsonGenerator> {

    private Long employeeId;
    private Long approverId;

    public VacationRequestJsonGenerator withEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public VacationRequestJsonGenerator withApproverId(Long approverId) {
        this.approverId = approverId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(VacationRequest object) {
        if (employeeId == null) {
            throw new IllegalStateException("Employee id must not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(writer);
        JsonGenerator jg = jsonGenerator
                .writeStartObject()
                .write("startDate", sdf.format(object.getStartDate()))
                .write("endDate", sdf.format(object.getEndDate()))
                .write("status", object.getStatus().toString())
                .write("employee", "/api/employees/" + employeeId);

        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        if (approverId != null) {
            jsonGenerator.write("approver", "/api/employees/" + approverId);
        }
        if (object.getApprovalDate() != null) {
            jg.write("approvalDate", sdf2.format(object.getApprovalDate()));
        }
        if (object.getNumberOfDays() != null) {
            jg.write("numberOfDays", object.getNumberOfDays());
        }
        if (object.getSubmissionTime() != null) {
            jg.write("submissionTime", sdf2.format(object.getSubmissionTime()));
        }

        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected VacationRequest getNewTransientObject(int i) {
        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        vacationRequest.setStartDate(new Date());
        vacationRequest.setEndDate(new Date());
        return vacationRequest;
    }

    @Override
    protected VacationRequestJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        employeeId = null;
        approverId = null;
    }
}
