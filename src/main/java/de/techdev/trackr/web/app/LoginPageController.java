package de.techdev.trackr.web.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Moritz Schulze
 */
@Controller
public class LoginPageController {

    @RequestMapping("/")
    public String root() {
        return "app/index.html";
    }

    @RequestMapping("/login")
    public String loginForm() {
        return "views/login.jsp";
    }

    @RequestMapping("/admin")
    public String adminForm() {
        return "views/admin.jsp";
    }
}
