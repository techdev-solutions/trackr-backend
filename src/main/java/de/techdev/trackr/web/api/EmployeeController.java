package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Credential;
import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.repository.EmployeeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @ResponseBody
    @RequestMapping(value = "/{employee}/self", method = {RequestMethod.PUT, RequestMethod.PATCH}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SelfEmployee updateSelf(@PathVariable("employee") Long employeeId, @RequestBody SelfEmployee selfEmployee) {
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
    @ResponseBody
    public SelfEmployee get(@PathVariable("employee") Long employeeId) {
        Employee employee = employeeRepository.findOne(employeeId);
        return SelfEmployee.valueOf(employee);
    }

    /**
     * Create a new employee along with his/her credentials. Used so binding errors for both entities are displayed at the same time.
     * @param createEmployee Wrapper for an employee and a credential entity.
     * @param bindingResult Spring binding result.
     * @return The newly created employee
     * @throws BindException If the bindingResult had errors.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/createWithCredential", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createWithCredential(@RequestBody @Valid CreateEmployee createEmployee, BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        createEmployee.getEmployee().setCredential(createEmployee.getCredential());
        createEmployee.getCredential().setEmployee(createEmployee.getEmployee());

        return employeeRepository.save(createEmployee.getEmployee());
    }

    /**
     * Wrapper DTO for an employee and a credential.
     * <p>
     * This class <b>must</b> be static, otherwise the binding errors will not work correctly.
     */
    @Data
    protected static class CreateEmployee {
        @Valid
        private Employee employee;

        @Valid
        private Credential credential;
    }
}