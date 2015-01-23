package de.techdev.trackr.domain;

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

}
