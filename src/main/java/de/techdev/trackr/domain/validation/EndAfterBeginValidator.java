package de.techdev.trackr.domain.validation;

import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import org.springframework.beans.DirectFieldAccessor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class EndAfterBeginValidator implements ConstraintValidator<EndAfterBegin, Object> {

    private String beginFieldName;
    private String endFieldName;
    private String messageTemplate;

    @Override
    public void initialize(EndAfterBegin constraintAnnotation) {
        beginFieldName = constraintAnnotation.begin();
        endFieldName = constraintAnnotation.end();
        messageTemplate = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        DirectFieldAccessor dfa = new DirectFieldAccessor(value);
        Class<?> beginFieldType = dfa.getPropertyType(beginFieldName);
        Class<?> endFieldType = dfa.getPropertyType(endFieldName);

        if(! (Date.class.isAssignableFrom(beginFieldType) || Date.class.isAssignableFrom(endFieldType)) ) {
            throw new IllegalArgumentException("Fields are not date objects.");
        }
        Date beginField = (Date) dfa.getPropertyValue(beginFieldName);
        Date endField = (Date) dfa.getPropertyValue(endFieldName);

        boolean isValid = beginField == null || endField == null || !beginField.after(endField);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.
                    buildConstraintViolationWithTemplate(messageTemplate)
                    .addPropertyNode(endFieldName).addConstraintViolation();
        }
        return isValid;
    }
}
