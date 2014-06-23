package de.techdev.trackr.core.web.api;

import org.springframework.data.repository.support.Repositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.Link;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Overrides the {@link #linkToSingleResource(Class, Object)} method to not include {?projection}.
 * @author Moritz Schulze
 */
public class RepositoryEntityLinksWithoutProjection extends RepositoryEntityLinks {

    private final ResourceMappings mappings;
    private final PluginRegistry<BackendIdConverter, Class<?>> idConverters;

    /**
     * Creates a new {@link org.springframework.data.rest.webmvc.support.RepositoryEntityLinks}.
     *
     * @param repositories must not be {@literal null}.
     * @param mappings     must not be {@literal null}.
     * @param config       must not be {@literal null}.
     * @param resolver     must not be {@literal null}.
     * @param idConverters must not be {@literal null}.
     */
    public RepositoryEntityLinksWithoutProjection(Repositories repositories, ResourceMappings mappings, RepositoryRestConfiguration config, HateoasPageableHandlerMethodArgumentResolver resolver, PluginRegistry<BackendIdConverter, Class<?>> idConverters) {
        super(repositories, mappings, config, resolver, idConverters);
        this.mappings = mappings;
        this.idConverters = idConverters;
    }

    @Override
    public Link linkToSingleResource(Class<?> type, Object id) {
        Assert.isInstanceOf(Serializable.class, id, "Id must be assignable to Serializable!");

        ResourceMetadata metadata = mappings.getMappingFor(type);
        String mappedId = idConverters.getPluginFor(type, BackendIdConverter.DefaultIdConverter.INSTANCE).toRequestId((Serializable) id, type);
        return linkFor(type).slash(mappedId).withRel(metadata.getItemResourceRel());
    }
}
