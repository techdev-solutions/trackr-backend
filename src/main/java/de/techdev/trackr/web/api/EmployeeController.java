package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * A controller for special operations on Employees not exportable by spring-data-rest
 *
 * @author Moritz Schulze
 */
@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * This method allows an employee to change some values of his entity on his own, namely the one
     * in SelfEmployee.
     *
     * @param employeeId The id of the employee. Only if the principal has the same id access is allowed.
     * @param selfEmployee The request body, i.e. the data to change
     * @return The updated data.
     */
    @PreAuthorize("isAuthenticated() and #employeeId == principal.id")
    @RequestMapping(value = "/{employee}/self", method = {RequestMethod.PUT, RequestMethod.PATCH}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SelfEmployee updateSelf(@PathVariable("employee") Long employeeId, @RequestBody SelfEmployee selfEmployee) {
        Employee employee = employeeRepository.findOne(employeeId);
        //Since patch is allowed all fields can be null and shouldn't be overwritten in that case.
        if (selfEmployee.getFirstName() != null) {
            employee.setFirstName(selfEmployee.getFirstName());
        }
        if (selfEmployee.getLastName() != null) {
            employee.setLastName(selfEmployee.getLastName());
        }
        if (selfEmployee.getPhoneNumber() != null) {
            employee.setPhoneNumber(selfEmployee.getPhoneNumber());
        }
        employeeRepository.save(employee);
        return SelfEmployee.valueOf(employee);
    }

    @RequestMapping(value = "/{employee}/self", method = {RequestMethod.GET})
    public @ResponseBody SelfEmployee get(@PathVariable("employee") Long employeeId) {
        Employee employee = employeeRepository.findOne(employeeId);
        return SelfEmployee.valueOf(employee);
    }
}
