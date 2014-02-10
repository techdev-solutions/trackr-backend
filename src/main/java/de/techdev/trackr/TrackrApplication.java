package de.techdev.trackr;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Moritz Schulze
 */
@Configuration
@Import({WebMvcConfiguration.class})
public class TrackrApplication {
}
