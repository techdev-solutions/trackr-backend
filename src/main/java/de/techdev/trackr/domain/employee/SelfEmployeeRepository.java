package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.company.Address;
import de.techdev.trackr.domain.company.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SelfEmployeeRepository {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * Update all values on an employee that an employee is allowed to update him/herself.
     * This is the last name, first name, phone number and address.
     * @param employeeId The id of the employee to update.
     * @param selfEmployee The new data for the employee.
     * @return The updated employee
     */
    @Transactional
    @PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.email == principal?.username")
    public Employee save(Long employeeId, SelfEmployee selfEmployee) {
        Employee employee = employeeRepository.findOne(employeeId);
        employee.setFirstName(selfEmployee.getFirstName());
        employee.setLastName(selfEmployee.getLastName());
        employee.setPhoneNumber(selfEmployee.getPhoneNumber());

        if (selfEmployee.getAddress() != null) {
            Address address = addressRepository.save(selfEmployee.getAddress());
            employee.setAddress(address);
        }

        return employeeRepository.save(employee);
    }

    public SelfEmployee findOne(Long employeeId) {
        return SelfEmployee.valueOf(employeeRepository.findOne(employeeId));
    }
}
