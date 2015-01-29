package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.test.rest.AbstractJsonGenerator;
import de.techdev.trackr.util.LocalDateUtil;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class SickDaysJsonGenerator extends AbstractJsonGenerator<SickDays, SickDaysJsonGenerator> {

    private Long employeeId;

    public SickDaysJsonGenerator withEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    @Override
    protected String getJsonRepresentation(SickDays object) {
        if (employeeId == null) {
            throw new IllegalStateException("employee id must not be null");
        }
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
          .write("startDate", sdf.format(object.getStartDate()))
          .write("employee", "/api/employees/" + employeeId);
        if (object.getEndDate() != null) {
            jg.write("endDate", sdf.format(object.getEndDate()));
        }


        if (object.getId() != null) {
            jg.write("id", object.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected SickDays getNewTransientObject(int i) {
        SickDays sickDays = new SickDays();
        LocalDate now = LocalDate.now();
        sickDays.setStartDate(LocalDateUtil.fromLocalDate(now));
        if (i % 2 == 0) {
            sickDays.setEndDate(LocalDateUtil.fromLocalDate(now.plusDays(i)));
        }
        return sickDays;
    }

    @Override
    protected SickDaysJsonGenerator getSelf() {
        return this;
    }

    @Override
    protected void reset() {
        employeeId = null;
    }
}
