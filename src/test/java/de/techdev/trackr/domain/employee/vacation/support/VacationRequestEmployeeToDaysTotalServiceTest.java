package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VacationRequestEmployeeToDaysTotalServiceTest {

    VacationRequestEmployeeToDaysTotalService service;

    @Before
    public void setUp() throws Exception {
        service = new VacationRequestEmployeeToDaysTotalService();
    }

    @Test
    public void testGetMinimum() throws Exception {
        Date date1 = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 7, 1));
        Date date2 = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 7, 2));
        Date minimum = service.getMinimum(date1, date2);
        assertThat(minimum, is(date1));
    }

    @Test
    public void testGetMaximum() throws Exception {
        Date date1 = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 7, 1));
        Date date2 = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 7, 2));
        Date maximum = service.getMaximum(date1, date2);
        assertThat(maximum, is(date2));
    }

    @Test
    public void mapEmployeesAndSumUp() throws Exception {
        Employee empl1 = new Employee();
        empl1.setFirstName("first_name_1");
        empl1.setLastName("last_name_1");
        Employee empl2 = new Employee();
        empl2.setFirstName("first_name_2");
        empl2.setLastName("last_name_2");
        VacationRequest vr1 = new VacationRequest();
        vr1.setEmployee(empl1);
        VacationRequest vr2 = new VacationRequest();
        vr2.setEmployee(empl1);
        VacationRequest vr3 = new VacationRequest();
        vr3.setEmployee(empl2);

        Map<String, Integer> employeeDayMapping = service.mapToEmployeesAndSumUp(asList(vr1, vr2, vr3), vr -> 1);
        assertThat(employeeDayMapping.keySet().size(), is(2));
        assertThat(employeeDayMapping.get("first_name_1 last_name_1"), is(2));
        assertThat(employeeDayMapping.get("first_name_2 last_name_2"), is(1));
    }
}