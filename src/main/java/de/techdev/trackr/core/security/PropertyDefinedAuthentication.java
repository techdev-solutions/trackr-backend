package de.techdev.trackr.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

import de.techdev.trackr.core.security.auth.AuthenticationType;

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
	public  UserFromPropertyList userFromPropertyList() {
		return new UserFromPropertyList();
	}

}