package de.techdev.trackr.domain.project.worktimes;

import de.techdev.test.rest.AbstractJsonGenerator;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkTimeJsonGenerator extends AbstractJsonGenerator<WorkTime, WorkTimeJsonGenerator> {

    private Long employeeId;
    private Long projectId;

    public WorkTimeJsonGenerator withEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public WorkTimeJsonGenerator withProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(WorkTime object) {
        StringWriter writer = new StringWriter();
        if (projectId == null || employeeId == null) {
            throw new IllegalStateException("employee id and project id must be set!");
        }
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
          .write("date", sdf.format(object.getDate()))
          .write("startTime", object.getStartTime().toString())
          .write("endTime", object.getEndTime().toString())
          .write("employee", "/employees/" + employeeId)
          .write("project", "/projects/" + projectId);

        if (object.getComment() != null) {
            jg.write("comment", object.getComment());
        }

        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected WorkTime getNewTransientObject(int i) {
        WorkTime workTime = new WorkTime();
        workTime.setDate(new Date());
        workTime.setStartTime(Time.valueOf("09:00:00"));
        workTime.setEndTime(Time.valueOf("17:00:00"));
        workTime.setComment("comment_" + i);
        return workTime;
    }

    @Override
    protected WorkTimeJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        projectId = null;
        employeeId = null;
    }
}
