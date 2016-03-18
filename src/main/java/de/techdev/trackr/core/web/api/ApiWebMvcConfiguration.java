package de.techdev.trackr.core.web.api;

import de.techdev.trackr.core.web.converters.DateConverter;
import de.techdev.trackr.domain.common.EmployeeSettingsLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;

@Configuration
public class ApiWebMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Autowired
    private DateConverter dateConverter;

    /**
     * The self HREF for entities contains {?projection} to indicate the parameter is available, but we don't want that.
     * The {@link RepositoryEntityLinksWithoutProjection} switches {?projection} off.
     */
    @Override
    @Bean
    public RepositoryEntityLinks entityLinks() {
        ArgumentResolverPagingAndSortingTemplateVariables variables = new ArgumentResolverPagingAndSortingTemplateVariables(this.pageableResolver(), this.sortResolver());
        return new RepositoryEntityLinksWithoutProjection(repositories(), resourceMappings(), config(), variables, backendIdConverterRegistry());
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new EmployeeSettingsLocaleResolver();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // super call is needed so the DomainClassConverter is used for linked resources
        // (e.g. companies/0/address). The super class will configure the DomainClassConverter correctly only with this
        // super call.
        super.addFormatters(registry);
        // We need to add the converter so non-Spring-Data-REST calls also use it.
        registry.addConverter(dateConverter);
    }

    /**
     * Needed for mapping exceptions in jackson that otherwise would get an ugly error message.
     */
    @Bean
    public JsonMappingHandlerExceptionResolver jsonMappingHandlerExceptionResolver() {
        return new JsonMappingHandlerExceptionResolver();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(jsonMappingHandlerExceptionResolver());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

}
