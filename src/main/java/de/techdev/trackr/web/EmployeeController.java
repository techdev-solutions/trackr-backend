package de.techdev.trackr.web;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/api/users/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "current", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Employee current() {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        return employeeRepository.findByCredentials_Email(email);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Employee> employees() {
        return employeeRepository.findAll();
    }
}
