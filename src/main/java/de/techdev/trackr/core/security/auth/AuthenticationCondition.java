package de.techdev.trackr.core.security.auth;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import de.techdev.trackr.core.security.SecurityConfiguration;

/**
 * {@link Condition} that matches based on the value of a {@link AuthenticationType @AuthenticationType}
 * annotation.
 *
 * @author Lars Sadau
 */
public class AuthenticationCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (context.getEnvironment() != null) {
			MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(AuthenticationType.class.getName());
			if (attrs != null) {
				for (Object value : attrs.get("value")) {
					String authmodule = context.getEnvironment().getProperty(SecurityConfiguration.AUTH_MODULE_PROPERTY_NAME);
					String valueAsString = ((String[]) value)[0];
					if (authmodule!=null && authmodule.equals(valueAsString) ) {
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}

}
