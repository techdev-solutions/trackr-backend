package de.techdev.trackr.domain;

import de.techdev.trackr.domain.employee.login.CredentialDataOnDemand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author Moritz Schulze
 */
@Configuration
@ComponentScan(basePackages = {"de.techdev.trackr.domain"},
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AbstractDataOnDemand.class)
        })
public class DataOnDemandConfiguration {

    /**
     * Doesn't extend {@link de.techdev.trackr.domain.AbstractDataOnDemand}, so we instantiate ourselves.
     */
    @Bean
    public CredentialDataOnDemand credentialDataOnDemand() {
        return new CredentialDataOnDemand();
    }
}
