package de.techdev.trackr.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.MediaType;
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
    private SelfEmployeeRepository employeeRepository;

    /**
     * This method allows an employee to change some values of his entity on his own, namely the one
     * in SelfEmployee.
     *
     * @param employee The employee to edit.
     * @param selfEmployee The request body, i.e. the data to change
     * @return The updated data.
     */
    @ResponseBody
    @RequestMapping(value = "/{employee}/self", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SelfEmployee updateSelf(@PathVariable("employee") Long employee,
                                   @RequestBody @Valid SelfEmployee selfEmployee,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        return SelfEmployee.valueOf(employeeRepository.save(employee, selfEmployee));
    }

    @RequestMapping(value = "/{employee}/self", method = {RequestMethod.GET})
    @ResponseBody
    public SelfEmployee get(@PathVariable("employee") Long employeeId) {
        return employeeRepository.findOne(employeeId);
    }
}