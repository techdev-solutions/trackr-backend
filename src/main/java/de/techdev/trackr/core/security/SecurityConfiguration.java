package de.techdev.trackr.core.security;

import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.TrackrUser;
import de.techdev.trackr.domain.employee.login.TrackrUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationFilter;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import java.util.Locale;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Configuration
@Import(OAuth2ServerConfiguration.class)
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

    @Override
    public void configure(WebSecurity web) throws Exception {
        //TODO: this is just copied from the Spring Security example, don't know what these requests are for.
        web.ignoring()
                .antMatchers("/webjars/**", "/oauth_uncache_approvals", "/oauth/cache_approvals");
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

        http
            .authorizeRequests().antMatchers("/login", "/admin").permitAll()
            .and()
            .authorizeRequests().anyRequest().hasAnyRole("ADMIN", "EMPLOYEE", "SUPERVISOR")
            .and()
            .formLogin() //this is only for the admin account
                .loginPage("/login") //redirect to /login if no authenticated session is active
                .loginProcessingUrl("/login/admin") //form has to post to /login/admin
                .defaultSuccessUrl("/success", true)
            .and()
            .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
            /*.and()
            .openidLogin()
                .loginPage("/login") //see above
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login")
                .authenticationUserDetailsService(trackrUserDetailsService()) //use our user detail service to map google accounts to techdev accounts
                .attributeExchange("https://www.google.com/.*")
                    .attribute("email").required(true).type("http://schema.openid.net/contact/email")
                    .and()
                    .attribute("first").required(true).type("http://schema.openid.net/namePerson/first")
                    .and()
                    .attribute("last").required(true).type("http://schema.openid.net/namePerson/last")*/;
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
