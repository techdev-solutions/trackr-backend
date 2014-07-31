package de.techdev.trackr.domain.validation.constraints;

import de.techdev.trackr.domain.validation.ProjectBelongsToCompanyValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation that checks if a project field, if it is not null, belongs to a company field.
 *
 * @author Moritz Schulze
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProjectBelongsToCompanyValidator.class)
public @interface ProjectBelongsToCompany {

    Class[] groups() default {};

    Class[] payload() default {};

    String message() default "{validation.company.projectBelongsTo}";

    String companyField() default "company";

    String projectField() default "project";

}
