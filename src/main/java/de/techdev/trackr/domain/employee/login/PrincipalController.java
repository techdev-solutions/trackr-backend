package de.techdev.trackr.domain.employee.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author Moritz Schulze
 */
@Controller
public class PrincipalController {

    @Autowired
    private CredentialRepository credentialRepository;

    @RequestMapping(value = "/principal", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Credential principal(Principal principal) {
        if(principal != null) {
            return credentialRepository.findByEmail(principal.getName());
        } else {
            return null;
        }
    }
}
