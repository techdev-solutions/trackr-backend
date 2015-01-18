package de.techdev.trackr.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * A controller for special operations on Employees not exportable by spring-data-rest
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
     * @param employee The employee to edit.
     * @param selfEmployee The request body, i.e. the data to change
     * @return The updated data.
     */
    @PreAuthorize("#employee.email == principal?.username")
    @ResponseBody
    @RequestMapping(value = "/{employee}/self", method = {RequestMethod.PUT, RequestMethod.PATCH}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SelfEmployee updateSelf(@PathVariable("employee") Employee employee, @RequestBody @Valid SelfEmployee selfEmployee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
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
    @ResponseBody
    public SelfEmployee get(@PathVariable("employee") Long employeeId) {
        Employee employee = employeeRepository.findOne(employeeId);
        return SelfEmployee.valueOf(employee);
    }
}