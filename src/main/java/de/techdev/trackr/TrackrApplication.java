package de.techdev.trackr;

import de.techdev.trackr.security.MethodSecurityConfiguration;
import de.techdev.trackr.web.WebMvcConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Moritz Schulze
 */
@Configuration
@Import({WebMvcConfiguration.class, MethodSecurityConfiguration.class})
@PropertySource({"classpath:application_${spring.profiles.active:dev}.properties"})
public class TrackrApplication {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
