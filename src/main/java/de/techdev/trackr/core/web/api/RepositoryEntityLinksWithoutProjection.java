package de.techdev.trackr.core.web.api;

import org.springframework.data.repository.support.Repositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.data.rest.webmvc.support.PagingAndSortingTemplateVariables;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Overrides the {@link #linkToSingleResource(Class, Object)} method to not include {?projection}.
 * @author Moritz Schulze
 * @deprecated We really should make the frontend not rely on this and just remove it all together. For the migration to
 *             Spring Boot 1.3.3 it's left in so the REST API is as before as much as possible.
 */
@Deprecated
public class RepositoryEntityLinksWithoutProjection extends RepositoryEntityLinks {

    private final ResourceMappings mappings;
    private final PluginRegistry<BackendIdConverter, Class<?>> idConverters;

    /**
     * Creates a new {@link org.springframework.data.rest.webmvc.support.RepositoryEntityLinks}.
     *
     * @param repositories must not be {@literal null}.
     * @param mappings     must not be {@literal null}.
     * @param config       must not be {@literal null}.
     * @param variables     must not be {@literal null}.
     * @param idConverters must not be {@literal null}.
     */
    public RepositoryEntityLinksWithoutProjection(Repositories repositories, ResourceMappings mappings, RepositoryRestConfiguration config, PagingAndSortingTemplateVariables variables, PluginRegistry<BackendIdConverter, Class<?>> idConverters) {
        super(repositories, mappings, config, variables, idConverters);
        this.mappings = mappings;
        this.idConverters = idConverters;
    }

    @Override
    public Link linkToSingleResource(Class<?> type, Object id) {
        Assert.isInstanceOf(Serializable.class, id, "Id must be assignable to Serializable!");
        ResourceMetadata metadata = mappings.getMetadataFor(type);
        String mappedId = idConverters.getPluginFor(type, BackendIdConverter.DefaultIdConverter.INSTANCE).toRequestId((Serializable) id, type);
        return linkFor(type).slash(mappedId).withRel(metadata.getItemResourceRel());
    }
}
