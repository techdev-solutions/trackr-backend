package de.techdev.trackr.web;

import de.techdev.trackr.domain.Credentials;
import de.techdev.trackr.repository.CredentialsRepository;
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
public class TrackrController {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @RequestMapping(value = "/api/principal", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Credentials principal(Principal principal) {
        return credentialsRepository.findByEmail(principal.getName());
    }

    @RequestMapping("/")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/admin")
    public String adminForm() {
        return "admin";
    }
}
