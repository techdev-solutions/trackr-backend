package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PreAuthorize("isAuthenticated() and #employeeId == principal.id")
    @RequestMapping(value = "/{employee}/self", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public @ResponseBody SelfEmployee updateSelf(@PathVariable("employee") Long employeeId, @RequestBody SelfEmployee selfEmployee){
        Employee employee = employeeRepository.findOne(employeeId);
        if(selfEmployee.getFirstName() != null) {
            employee.setFirstName(selfEmployee.getFirstName());
        }
        if(selfEmployee.getLastName() != null) {
            employee.setLastName(selfEmployee.getLastName());
        }
        if(selfEmployee.getPhoneNumber() != null) {
            employee.setPhoneNumber(selfEmployee.getPhoneNumber());
        }
        employeeRepository.save(employee);
        return SelfEmployee.valueOf(employee);
    }

    @PreAuthorize("isAuthenticated() and #employeeId == principal.id")
    @RequestMapping(value = "/{employee}/self", method = {RequestMethod.GET})
    public @ResponseBody SelfEmployee get(@PathVariable("employee") Long employeeId){
        Employee employee = employeeRepository.findOne(employeeId);
        return SelfEmployee.valueOf(employee);
    }
}
