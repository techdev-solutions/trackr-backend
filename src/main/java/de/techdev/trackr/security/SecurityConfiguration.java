package de.techdev.trackr.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("techdev").roles("ADMIN");
    }

    /**
     * INFO: for some reason this has to be the interface or the proxy cannot be cast.
     * @return The custom user detail service
     */
    @Bean
    public AuthenticationUserDetailsService<OpenIDAuthenticationToken> trackrUserDetailsService() {
        return new TrackrUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO: this configuration is currently only being used in the API servlet but also secures the app.
        http.authorizeRequests()
                .antMatchers("/", "/app/src/bower_components/**").permitAll() //the login page should be able to access CSS and JS files
                .antMatchers("/app/**").authenticated()
                .antMatchers("/api/**").authenticated();

        http.logout().logoutUrl("/logout");

        http.
            formLogin() //this is only for the admin account
                .loginPage("/") //redirect to / if no authenticated session is active
                .loginProcessingUrl("/login/admin") //form has to post to /login/admin
                .defaultSuccessUrl("/app/index.html")
            .and()
            .openidLogin()
                .loginPage("/") //see above
                .defaultSuccessUrl("/app/index.html")
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
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        //TODO make this configurable
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF ROLE_STAFF > ROLE_EMPLOYEE");
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
