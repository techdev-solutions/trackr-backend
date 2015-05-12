package de.techdev.trackr.domain.employee;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * A class used to transfer data that one employee can change about himself
 *
 * @author Moritz Schulze
 */
@Getter
@Setter
public class SelfEmployee {

    @NotEmpty
    private String firstName;

    @NotEmpty
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
