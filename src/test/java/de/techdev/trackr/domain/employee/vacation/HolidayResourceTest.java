package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Test;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.isAccessible;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isMethodNotAllowed;
import static org.junit.Assert.*;

public class HolidayResourceTest extends AbstractDomainResourceTest<Holiday> {

    @Override
    protected String getResourceName() {
        return "holidays";
    }

    @Test
    public void rootAccessible() throws Exception {
        assertThat(root(employeeSession()), isAccessible());
    }

    @Test
    public void oneAccessible() throws Exception {
        assertThat(one(employeeSession()), isAccessible());
    }

    @Test
    public void createNotExported() throws Exception {
        assertThat(create(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void updateNotExported() throws Exception {
        assertThat(update(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void deleteNotExported() throws Exception {
        assertThat(remove(employeeSession()), isMethodNotAllowed());
    }

    @Override
    protected String getJsonRepresentation(Holiday holiday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(writer);
        JsonGenerator jg = jsonGenerator
                .writeStartObject()
                .write("startDate", sdf.format(holiday.getDay()))
                .write("status", holiday.getName())
                .write("federalState", holiday.getFederalState().toString());

        if (holiday.getId() != null) {
            jg.write("id", holiday.getId());
        }

        jg.writeEnd().close();
        return writer.toString();
    }
}