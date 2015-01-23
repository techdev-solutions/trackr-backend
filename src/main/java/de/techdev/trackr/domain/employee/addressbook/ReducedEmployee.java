package de.techdev.trackr.domain.employee.addressbook;

import de.techdev.trackr.domain.employee.Employee;
import lombok.Data;

/**
 * Employee only containing the information needed for the address book.
 */
@Data
public class ReducedEmployee {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String title;

    private String email;

    static ReducedEmployee valueOf(Employee employee) {
        ReducedEmployee reducedEmployee = new ReducedEmployee();
        reducedEmployee.setFirstName(employee.getFirstName());
        reducedEmployee.setLastName(employee.getLastName());
        reducedEmployee.setPhoneNumber(employee.getPhoneNumber());
        reducedEmployee.setTitle(employee.getTitle());
        reducedEmployee.setEmail(employee.getEmail());
        return reducedEmployee;
    }
}