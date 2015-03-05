package de.techdev.trackr.domain.project.billtimes;

import de.techdev.test.rest.AbstractJsonGenerator;
import de.techdev.trackr.util.LocalDateUtil;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class BillableTimesJsonGenerator extends AbstractJsonGenerator<BillableTime, BillableTimesJsonGenerator> {

    private Long employeeId;
    private Long projectId;

    public BillableTimesJsonGenerator withEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public BillableTimesJsonGenerator withProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(BillableTime object) {
        StringWriter writer = new StringWriter();
        if (employeeId == null || projectId == null) {
            throw new IllegalStateException("Employee id and project id must not be null.");
        }
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
          .write("date", sdf.format(object.getDate()))
          .write("minutes", object.getMinutes())
          .write("employee", "/employees/" + employeeId)
          .write("project", "/projects/" + projectId);
        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected BillableTime getNewTransientObject(int i) {
        BillableTime billableTime = new BillableTime();
        LocalDate localDate = LocalDate.now().with(ChronoField.DAY_OF_YEAR, (i % 356) + 1);
        billableTime.setDate(LocalDateUtil.fromLocalDate(localDate));
        billableTime.setMinutes(i);
        return billableTime;
    }

    @Override
    protected BillableTimesJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        employeeId = null;
        projectId = null;
    }
}
