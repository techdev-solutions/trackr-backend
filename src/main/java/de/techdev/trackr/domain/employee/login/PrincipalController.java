package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import de.techdev.trackr.domain.employee.settings.Settings;
import de.techdev.trackr.domain.employee.settings.SettingsRepository;
import de.techdev.trackr.domain.employee.settings.SettingsType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collection;

@Controller
@Slf4j
public class PrincipalController {

    @Data
    static class ReturnValue {
        Collection<? extends GrantedAuthority> authorities;
        String locale;
        Long id;
        String email;
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @RequestMapping(value = "/principal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ReturnValue principal(Principal principal) {
        if (principal != null) {
            Employee employee = employeeRepository.findByEmail(principal.getName());
            if (employee != null) {
                Settings localeSettings = settingsRepository.findByTypeAndEmployee_Email(SettingsType.LOCALE, employee.getEmail());
                ReturnValue value = new ReturnValue();
                value.locale = localeSettings.getValue();
                value.id = employee.getId();
                value.authorities = ((Authentication) principal).getAuthorities();
                value.email = principal.getName();
                return value;
            } else {
                log.error("Somehow someone with an invalid email is in our system: {}", principal.getName());
                throw new IllegalStateException("Invalid email address in principal.");
            }
        } else {
            return null;
        }
    }
}
