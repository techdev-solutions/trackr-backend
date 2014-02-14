package de.techdev.trackr.web.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Moritz Schulze
 */
@Controller
public class LoginPageController {

    @RequestMapping("/")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/admin")
    public String adminForm() {
        return "admin";
    }
}
