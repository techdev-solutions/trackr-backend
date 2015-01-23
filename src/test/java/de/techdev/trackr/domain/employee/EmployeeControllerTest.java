package de.techdev.trackr.domain.employee;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends MockMvcTest {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Test
    public void updateSelfViaPatch() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/employees/" + employee.getId() + "/self")
                        .session(employeeSession(employee.getEmail()))
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE) //TODO: why do we have to do this ourself?
                        .content("{\"firstName\": \"asd\", \"lastName\": \"asdf\", \"phoneNumber\": \"12345\"}")
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("phoneNumber", is("12345")))
               .andExpect(jsonPath("firstName", is(employee.getFirstName())))
               .andExpect(jsonPath("lastName", is(employee.getLastName())));
    }

    @Test
    public void updateSelfViaPatchReturns400() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/employees/" + employee.getId() + "/self")
                        .session(employeeSession(employee.getEmail()))
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"phoneNumber\": \"12345\"}")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOtherViaPatchIsForbidden() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/employees/" + employee.getId() + "/self")
                        .session(employeeSession(employee.getEmail() + 1))
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"phoneNumber\": \"12345\"}")
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void updateSelfViaPut() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/employees/" + employee.getId() + "/self")
                        .session(employeeSession(employee.getEmail()))
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .content(generateEmployeeJson(SelfEmployee.valueOf(employee)))
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("firstName", is(employee.getFirstName())));
    }

    @Test
    public void updateOthersViaPutIsForbidden() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/employees/" + employee.getId() + "/self")
                        .session(employeeSession(employee.getEmail() + 1))
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .content(generateEmployeeJson(SelfEmployee.valueOf(employee)))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void accessSelf() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/employees/" + employee.getId() + "/self")
                        .session(employeeSession(employee.getEmail()))
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("firstName", is(employee.getFirstName())));
    }

    protected String generateEmployeeJson(SelfEmployee selfEmployee) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("firstName", selfEmployee.getFirstName())
          .write("lastName", selfEmployee.getLastName());
        if (selfEmployee.getPhoneNumber() != null) {
            jg.write("phoneNumber", selfEmployee.getPhoneNumber());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}
