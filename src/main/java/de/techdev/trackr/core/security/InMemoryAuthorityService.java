package de.techdev.trackr.core.security;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Profile("!oauth")
public class InMemoryAuthorityService implements AuthorityService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Collection<GrantedAuthority> getByEmployeeMail(String email) {
        return asList(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
    }

    @Override
    public Collection<String> getEmployeeEmailsByAuthority(String authority) {
        return employeeRepository.findAllForAddressBook(new PageRequest(0, 100)).getContent().stream().map(Employee::getEmail).collect(Collectors.toList());
    }

}
