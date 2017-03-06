package de.techdev.trackr.domain.validation;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.validation.constraints.ProjectBelongsToCompany;
import org.springframework.beans.DirectFieldAccessor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Moritz Schulze
 */
public class ProjectBelongsToCompanyValidator implements ConstraintValidator<ProjectBelongsToCompany, Object> {

    private String projectFieldName;
    private String companyFieldName;
    private String messageTemplateName;

    @Override
    public void initialize(ProjectBelongsToCompany constraintAnnotation) {
        companyFieldName = constraintAnnotation.companyField();
        projectFieldName = constraintAnnotation.projectField();
        messageTemplateName = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = true;
        DirectFieldAccessor dfa = new DirectFieldAccessor(value);
        Object project = dfa.getPropertyValue(projectFieldName);
        if (project != null) {
            Company company = (Company) dfa.getPropertyValue(companyFieldName);
            isValid = ((Project) project).getCompany().getId().equals(company.getId());
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageTemplateName)
                    .addPropertyNode(projectFieldName).addConstraintViolation();
        }
        return isValid;
    }
}
