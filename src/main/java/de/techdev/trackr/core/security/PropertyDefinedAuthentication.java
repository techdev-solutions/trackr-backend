package de.techdev.trackr.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import de.techdev.trackr.core.security.auth.AuthenticationType;
import de.techdev.trackr.core.security.auth.UserCredentialSupport;

@AuthenticationType({"property"})
@EnableGlobalAuthentication
@Configuration
public class PropertyDefinedAuthentication extends GlobalAuthenticationConfigurerAdapter {

	@Override
	public void init(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userFromPropertyList());
	}

	@Bean 
	public  UserCredentialSupport userCreationSupport(){
		return UserCredentialSupport.withCreateActivatedEmployees();
	}
	
	@Bean
	public  UserDetailsService userFromPropertyList() {
		return new UserFromPropertyList();
	}

}