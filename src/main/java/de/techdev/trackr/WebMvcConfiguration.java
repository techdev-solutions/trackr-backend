package de.techdev.trackr;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Moritz Schulze
 */
@Configuration
@ComponentScan(basePackages = "de.techdev.trackr.web")
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
}
