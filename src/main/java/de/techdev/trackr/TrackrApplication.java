package de.techdev.trackr;

import de.techdev.trackr.security.MethodSecurityConfiguration;
import de.techdev.trackr.web.WebMvcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Moritz Schulze
 */
@Configuration
@Import({WebMvcConfiguration.class, MethodSecurityConfiguration.class})
public class TrackrApplication {

}
