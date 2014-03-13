package de.techdev.trackr.domain.employee;

import lombok.Data;

/**
 * A class used to transfer data that one employee can change about himself
 *
 * @author Moritz Schulze
 */
@Data
public class SelfEmployee {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    public static SelfEmployee valueOf(Employee employee) {
        SelfEmployee selfEmployee = new SelfEmployee();
        selfEmployee.setFirstName(employee.getFirstName());
        selfEmployee.setLastName(employee.getLastName());
        selfEmployee.setPhoneNumber(employee.getPhoneNumber());
        return selfEmployee;
    }
}
