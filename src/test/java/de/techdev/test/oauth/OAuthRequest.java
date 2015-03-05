package de.techdev.test.oauth;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface OAuthRequest {

    /**
     * The roles for the OAuth access token
     */
    String[] value() default {"ROLE_EMPLOYEE"};

    /**
     * The username for the OAuth access token
     */
    String username() default "employee@techdev.de";

}
