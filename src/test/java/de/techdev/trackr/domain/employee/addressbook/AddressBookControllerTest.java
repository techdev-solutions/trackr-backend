package de.techdev.trackr.domain.employee.addressbook;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.login.Credential;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.junit.Assert.*;

public class AddressBookControllerTest {

    private AddressBookController addressBookController;

    @Before
    public void setUp() throws Exception {
        addressBookController = new AddressBookController();
    }

    @Test
    public void transformEmployees() throws Exception {
        Employee employee = new Employee();
        Credential credential = new Credential();
        employee.setCredential(credential);
        List<Employee> listOfEmployees = asList(employee);
        List<ReducedEmployee> reducedEmployees = addressBookController.transformToReducedEmployees(listOfEmployees);
        assertThat(reducedEmployees, isNotEmpty());
    }
}