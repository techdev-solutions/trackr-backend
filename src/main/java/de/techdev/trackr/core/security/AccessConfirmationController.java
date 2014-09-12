package de.techdev.trackr.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

/**
 * Displays the oauth confirmation page (do you want to allow client xy to access your data?)
 * @author Moritz Schulze
 */
@Controller
public class AccessConfirmationController {

    @Value("${proxy.path}")
    private String proxyPath;

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView confirmAccess(Map<String, Object> model, Principal principal) {
        //We set the proxy path so the form has the correct action. Since on local and QS we don't use a complete
        //URI we add a leading slash.
        if (proxyPath != null && proxyPath.startsWith("http")) {
            model.put("proxyPath", proxyPath);
        } else {
            model.put("proxyPath", "/" + proxyPath);
        }
        return new ModelAndView("confirm_access", model);
    }

    /*@RequestMapping("/oauth/error")
    public String handleError(Map<String, Object> model) throws Exception {
        // We can add more stuff to the model here for JSP rendering. If the client was a machine then
        // the JSON will already have been rendered.
        model.put("message", "There was a problem with the OAuth2 protocol");
        return "oauth_error";
    }*/
}
