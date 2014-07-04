package de.techdev.trackr.domain.employee.addressbook;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.login.Credential;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class AddressBookControllerTest {

    private AddressBookController addressBookController;

    @Before
    public void setUp() throws Exception {
        addressBookController = new AddressBookController();
    }

    @Test
    public void transformToReducedEmployeesAndRemoveAdminRemovesAdmin() throws Exception {
        Credential adminCredential = new Credential();
        adminCredential.setEmail(AddressBookController.ADMIN_EMAIL);
        Employee admin = new Employee();
        admin.setCredential(adminCredential);

        Credential normalCredential = new Credential();
        normalCredential.setEmail("user@techdev.de");
        Employee normal = new Employee();
        normal.setCredential(normalCredential);

        List<ReducedEmployee> reducedEmployees = addressBookController.transformToReducedEmployeesAndRemoveAdmin(asList(admin, normal));
        for (ReducedEmployee reducedEmployee : reducedEmployees) {
            assertThat(reducedEmployee.getEmail(), is(not(AddressBookController.ADMIN_EMAIL)));
        }
        assertThat(reducedEmployees, isNotEmpty());
    }

    @Test
    public void transformEmployees() throws Exception {
        Employee employee = new Employee();
        Credential credential = new Credential();
        employee.setCredential(credential);
        List<Employee> listOfEmployees = asList(employee);
        List<ReducedEmployee> reducedEmployees = addressBookController.transformToReducedEmployeesAndRemoveAdmin(listOfEmployees);
        assertThat(reducedEmployees, isNotEmpty());
    }
}