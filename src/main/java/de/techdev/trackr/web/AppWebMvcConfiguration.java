package de.techdev.trackr.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Moritz Schulze
 */
@Configuration
@ComponentScan(basePackages = "de.techdev.trackr.web.app")
@EnableWebMvc
@PropertySource({"classpath:application_${spring.profiles.active:dev}.properties"})
public class AppWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${angular.path}")
    private String angularPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //this is only needed for the login page, the view Resolver seems to want to load "WEB-INF/app/index.html" instead of "/WEB-INF/...".
        registry.addResourceHandler("WEB-INF/app/*.html").addResourceLocations(angularPath);
        registry.addResourceHandler("/**").addResourceLocations(angularPath);
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        return viewResolver;
    }

}
