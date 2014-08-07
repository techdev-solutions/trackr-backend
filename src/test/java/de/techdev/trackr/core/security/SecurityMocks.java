package de.techdev.trackr.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Moritz Schulze
 */
@Configuration
public class SecurityMocks {

    @Bean
    public RemoveTokenService removeTokenService() {
        return (clientId, userName) -> {};
    }
}
