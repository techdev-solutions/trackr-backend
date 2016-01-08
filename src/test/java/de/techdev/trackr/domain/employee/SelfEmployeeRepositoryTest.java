package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.company.Address;
import de.techdev.trackr.domain.company.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelfEmployeeRepositoryTest {

    @InjectMocks
    private SelfEmployeeRepository selfEmployeeRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AddressRepository addressRepository;

    @Before
    public void setUp() throws Exception {
        Employee employee = new Employee();
        employee.setId(0L);
        employee.setTitle("oldTitle");
        employee.setSalary(BigDecimal.ONE);
        when(employeeRepository.findOne(eq(0L))).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenAnswer(inv -> inv.getArguments()[0]);
        when(addressRepository.save(any(Address.class))).thenAnswer(inv -> inv.getArguments()[0]);
    }

    @Test
    public void updateSelfUpdatesAllAllowedFields() throws Exception {
        SelfEmployee selfEmployee = new SelfEmployee();
        selfEmployee.setId(0l);
        selfEmployee.setFirstName("firstName");
        selfEmployee.setLastName("lastName");
        selfEmployee.setPhoneNumber("12345");

        Employee updatedEmployee = selfEmployeeRepository.save(0L, selfEmployee);
        assertThat(updatedEmployee.getId(), is(0l));
        assertThat(updatedEmployee.getFirstName(), is("firstName"));
        assertThat(updatedEmployee.getLastName(), is("lastName"));
        assertThat(updatedEmployee.getPhoneNumber(), is("12345"));
    }

    @Test
    public void updateSelfDoesNotUpdateForbiddenFields() throws Exception {
        SelfEmployee selfEmployee = new SelfEmployee();
        selfEmployee.setId(0l);
        selfEmployee.setSalary(BigDecimal.TEN);
        selfEmployee.setTitle("title");

        Employee updatedEmployee = selfEmployeeRepository.save(0L, selfEmployee);
        assertThat(updatedEmployee.getTitle(), is("oldTitle"));
        assertThat(updatedEmployee.getSalary(), is(BigDecimal.ONE));
    }

    @Test
    public void updateSelfUpdatesTheAddressWhenItIsNotNull() throws Exception {
        SelfEmployee selfEmployee = new SelfEmployee();
        Address address = new Address();
        selfEmployee.setAddress(address);

        Employee updatedEmployee = selfEmployeeRepository.save(0L, selfEmployee);

        verify(addressRepository).save(eq(address));
        assertThat(updatedEmployee.getAddress(), is(address));
    }
}