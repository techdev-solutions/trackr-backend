package de.techdev.trackr.security;

import de.techdev.trackr.security.TrackrUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Moritz Schulze
 */
@Configuration
@ImportResource({"classpath:META-INF/spring/spring-security.xml"})
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService trackrUserDetailsService() {
        return new TrackrUserDetailsService();
    }
}
