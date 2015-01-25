package de.techdev.trackr.core.web.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.techdev.trackr.core.security.SecurityConfiguration;

@Controller
public class LoginPageController {
	

    @Value(SecurityConfiguration.AUTH_MODULE_SPEL)
	String authModule;
	 

    @RequestMapping("/login")
    public String loginForm() {
    	if("openid".equals(authModule)){
    		return  "openid";
    	} else {
    		return "login"; 
    	}
    }


    @RequestMapping("/success")
    public String successPage() {
        return "success";
    }
}