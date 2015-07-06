package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.company.Address;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.math.BigDecimal;

@Getter
@Setter
public class SelfEmployee {

    private Long id;

    private Integer version;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String phoneNumber;

    private String title;

    private BigDecimal salary;

    @Valid
    private Address address;

    public static SelfEmployee valueOf(Employee employee) {
        SelfEmployee selfEmployee = new SelfEmployee();
        selfEmployee.setId(employee.getId());
        selfEmployee.setFirstName(employee.getFirstName());
        selfEmployee.setLastName(employee.getLastName());
        selfEmployee.setPhoneNumber(employee.getPhoneNumber());
        selfEmployee.setAddress(employee.getAddress());
        selfEmployee.setVersion(employee.getVersion());
        selfEmployee.setSalary(employee.getSalary());
        selfEmployee.setTitle(employee.getTitle());
        return selfEmployee;
    }

}
