package de.techdev.trackr.core.security;

import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.TrackrUser;
import de.techdev.trackr.domain.employee.login.TrackrUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationFilter;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebSecurity
@PropertySource({"classpath:application_${spring.profiles.active:dev}.properties"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> new TrackrUser("admin@techdev.de", "techdev", true, asList(new Authority("ROLE_ADMIN")), 0L, Locale.ENGLISH));
    }

    /**
     * INFO: for some reason this has to be the interface or the proxy cannot be cast.
     *
     * @return The custom user detail service
     */
    @Bean
    public AuthenticationUserDetailsService<OpenIDAuthenticationToken> trackrUserDetailsService() {
        return new TrackrUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(openIdReturnToFilter(), OpenIDAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/login/**", "/admin", "/src/vendor/**").permitAll() //the login page should be able to access CSS and JS files
                .antMatchers("/**").hasAnyRole("ADMIN", "SUPERVISOR", "EMPLOYEE") //TODO: currently this method does not know about the roleHierarchy so we have to specify all roles. This should be changed.
        ;

        http.logout().logoutUrl("/logout");

        /*If the session times out any request in the frontend will be redirected to /login. There's no way
        to tell that in the frontend because 302s are handled by the browser so we add a special header
        to requests for the login page.
        This header can be processed in the frontend to redirect the whole angular app to the login page.*/
        http.headers().addHeaderWriter((request, response) -> {
            if(request.getRequestURI().endsWith("/login")) {
                response.addHeader("trackr-login-page", "true");
            }
        });

        http.
            formLogin() //this is only for the admin account
                .loginPage("/login") //redirect to /login if no authenticated session is active
                .loginProcessingUrl("/login/admin") //form has to post to /login/admin
                .defaultSuccessUrl("/#/", true)
            .and()
            .openidLogin()
                .loginPage("/login") //see above
                .defaultSuccessUrl("/#/", true)
                .failureUrl("/")
                .authenticationUserDetailsService(trackrUserDetailsService()) //use our user detail service to map google accounts to techdev accounts
                .attributeExchange("https://www.google.com/.*")
                    .attribute("email").required(true).type("http://schema.openid.net/contact/email")
                    .and()
                    .attribute("first").required(true).type("http://schema.openid.net/namePerson/first")
                    .and()
                    .attribute("last").required(true).type("http://schema.openid.net/namePerson/last");
        //TODO: enable this
        http.csrf().disable();
    }

    @Bean
    public Filter openIdReturnToFilter() {
        return new OpenIDReturnToReplacementFilter();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        //TODO make this configurable
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_SUPERVISOR ROLE_SUPERVISOR > ROLE_EMPLOYEE ROLE_EMPLOYEE > ROLE_ANONYMOUS");
        return roleHierarchy;
    }

    @Bean
    public RoleVoter roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
