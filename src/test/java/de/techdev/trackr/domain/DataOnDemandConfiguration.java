package de.techdev.trackr.domain;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author Moritz Schulze
 */
@Configuration
@ComponentScan(basePackages = {"de.techdev.trackr"},
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.*DataOnDemand$")
        })
public class DataOnDemandConfiguration {

}
