package de.techdev.trackr.core.web.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Moritz Schulze
 */
@Controller
public class LoginPageController {

    /*
        TODO: I don't know why this controller is needed.
        Resolving the JSPs directly does not work.
     */

    @RequestMapping("/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/admin")
    public String adminForm() {
        return "admin";
    }

    @RequestMapping("/success")
    public String successPage() {
        return "success";
    }
}
