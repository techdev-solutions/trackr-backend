package de.techdev.trackr.core.web.api;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.support.PagingAndSortingTemplateVariables;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.HateoasSortHandlerMethodArgumentResolver;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Copy from Spring because thankfully they made it package protected. Nice!
 *
 * @deprecated See {@link RepositoryEntityLinksWithoutProjection}
 */
@Deprecated
class ArgumentResolverPagingAndSortingTemplateVariables implements PagingAndSortingTemplateVariables {
    private static final Set<Class<?>> SUPPORTED_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new Class[]{Pageable.class, Sort.class})));
    private final HateoasPageableHandlerMethodArgumentResolver pagingResolver;
    private final HateoasSortHandlerMethodArgumentResolver sortResolver;

    public ArgumentResolverPagingAndSortingTemplateVariables(HateoasPageableHandlerMethodArgumentResolver pagingResolver, HateoasSortHandlerMethodArgumentResolver sortResolver) {
        Assert.notNull(pagingResolver, "HateoasPageableHandlerMethodArgumentResolver must not be null!");
        Assert.notNull(sortResolver, "HateoasSortHandlerMethodArgumentResolver must not be null!");
        this.pagingResolver = pagingResolver;
        this.sortResolver = sortResolver;
    }

    public TemplateVariables getPaginationTemplateVariables(MethodParameter parameter, UriComponents components) {
        return this.pagingResolver.getPaginationTemplateVariables(parameter, components);
    }

    public TemplateVariables getSortTemplateVariables(MethodParameter parameter, UriComponents template) {
        return this.sortResolver.getSortTemplateVariables(parameter, template);
    }

    public void enhance(UriComponentsBuilder builder, MethodParameter parameter, Object value) {
        if(value instanceof Pageable) {
            this.pagingResolver.enhance(builder, parameter, value);
        } else if(value instanceof Sort) {
            this.sortResolver.enhance(builder, parameter, value);
        }

    }

    public boolean supportsParameter(MethodParameter parameter) {
        return SUPPORTED_TYPES.contains(parameter.getParameterType());
    }
}
