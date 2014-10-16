package de.techdev.trackr.core.web.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {
	

    @Value("${auth.module}")
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