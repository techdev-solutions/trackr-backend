package de.techdev.trackr.domain.employee;

import de.techdev.test.rest.AbstractJsonGenerator;
import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.util.LocalDateUtil;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class EmployeeJsonGenerator extends AbstractJsonGenerator<Employee, EmployeeJsonGenerator> {

    @Override
    protected String getJsonRepresentation(Employee employee) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
                .write("firstName", employee.getFirstName())
                .write("lastName", employee.getLastName())
                .write("hourlyCostRate", employee.getHourlyCostRate())
                .write("salary", employee.getSalary())
                .write("email", employee.getEmail())
                .write("title", employee.getTitle())
                .write("federalState", employee.getFederalState().getName());

        if(employee.getVacationEntitlement() != null) {
            jg.write("vacationEntitlement", employee.getVacationEntitlement());
        }

        if (employee.getJoinDate() != null) {
            jg.write("joinDate", sdf.format(employee.getJoinDate()));
        }

        if (employee.getLeaveDate() != null) {
            jg.write("leaveDate", sdf.format(employee.getLeaveDate()));
        }

        if (employee.getPhoneNumber() != null) {
            jg.write("phoneNumber", employee.getPhoneNumber());
        }

        if (employee.getId() != null) {
            jg.write("id", employee.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }

    @Override
    protected Employee getNewTransientObject(int i) {
        Employee employee = new Employee();
        employee.setFirstName("firstName_" + i);
        employee.setLastName("lastName_" + i);
        employee.setTitle("title_" + i);
        employee.setPhoneNumber("phoneNumber_" + i);
        employee.setSalary(BigDecimal.TEN);
        employee.setVacationEntitlement(30f);
        employee.setEmail("email" + i + "@techdev.de");
        employee.setHourlyCostRate(new BigDecimal("85.5"));
        employee.setJoinDate(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 1, 1)));
        employee.setFederalState(FederalState.BERLIN);
        return employee;
    }

    @Override
    protected EmployeeJsonGenerator getSelf() {
        return this;
    }
}
