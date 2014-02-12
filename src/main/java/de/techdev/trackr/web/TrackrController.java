package de.techdev.trackr.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@Controller
public class TrackrController {

    /**
     * This is just a test method te be called from the angular app
     * TODO: Delete this method as soon as real examples are available.
     *
     * @return The text "Welcome to trackr!"
     */
    @RequestMapping("/api/user")
    public @ResponseBody String user() {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        if (email == null) {
            return "No active user, how can this be?";
        } else {
            return email;
        }
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
