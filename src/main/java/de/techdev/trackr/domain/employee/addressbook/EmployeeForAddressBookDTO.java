package de.techdev.trackr.domain.employee.addressbook;

import de.techdev.trackr.domain.company.Address;
import de.techdev.trackr.domain.employee.Employee;
import lombok.Getter;
import lombok.Setter;

/**
 * Employee only containing the information needed for the address book.
 */
@Getter
@Setter
class EmployeeForAddressBookDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String title;

    private String email;

    private Address address;

    static EmployeeForAddressBookDTO valueOf(Employee employee) {
        EmployeeForAddressBookDTO employeeForAddressBookDTO = new EmployeeForAddressBookDTO();
        employeeForAddressBookDTO.setId(employee.getId());
        employeeForAddressBookDTO.setFirstName(employee.getFirstName());
        employeeForAddressBookDTO.setLastName(employee.getLastName());
        employeeForAddressBookDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeForAddressBookDTO.setTitle(employee.getTitle());
        employeeForAddressBookDTO.setEmail(employee.getEmail());
        employeeForAddressBookDTO.setAddress(employee.getAddress());
        return employeeForAddressBookDTO;
    }
}