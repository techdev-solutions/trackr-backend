package de.techdev.trackr.core.security.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.techdev.trackr.core.security.SecurityConfiguration;

/**
 * Indicates that a component is eligible for registration when one of the {@linkplain
 * #value specified authentication types} is active.
 *
 * <p>A <em>authentication type</em> can be activated
 * declaratively through setting the {@link SecurityConfiguration#AUTH_MODULE_PROPERTY_NAME
 * auth.module} property, usually through JVM system properties, as an
 * environment variable, or for web applications as a Servlet context parameter in
 * {@code web.xml}.
 *
 * <p>The {@code @AuthenticationType} annotation may be used in any of the following ways:
 * <ul>
 * <li>as a type-level annotation on any class directly or indirectly annotated with
 * {@code @Component}, including {@link Configuration @Configuration} classes</li>
 * <li>as a meta-annotation, for the purpose of composing custom stereotype annotations</li>
 * <li>as a method-level annotation on any {@link Bean @Bean} method</li>
 * </ul>
 *
 * <p>If a {@code @Configuration} class is marked with {@code @AuthenticationType}, all of the
 * {@code @Bean} methods and {@link Import @Import} annotations associated with that class
 * will be bypassed unless one of the specified authentication types is active.
 *
 * 
 * @author Lars Sadau
 * @see SecurityConfiguration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(AuthenticationCondition.class)
public @interface AuthenticationType {
	/**
	 * The set of authentication types for which the annotated component should be registered.
	 */
	String[] value();
}
