package de.techdev.trackr.core.web.app;

import de.techdev.trackr.core.security.AccessConfirmationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebMvc
@PropertySources({
        @PropertySource({"classpath:application_${spring.profiles.active:dev}.properties"}),
        @PropertySource(value = "${trackr.externalconfig:file:/etc/trackr.properties}", ignoreResourceNotFound = true)
})
public class AppWebMvcConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Needed to load the .properties file.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }

    /**
     * This controller displays the JSPs.
     */
    @Bean
    public LoginPageController loginPageController() {
        return new LoginPageController();
    }

    /**
     * Controller for a custom OAuth confirmation page.
     */
    @Bean
    public AccessConfirmationController accessConfirmationController() {
        return new AccessConfirmationController();
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        contentNegotiatingViewResolver.setViewResolvers(asList(viewResolver));
        return contentNegotiatingViewResolver;
    }
}
