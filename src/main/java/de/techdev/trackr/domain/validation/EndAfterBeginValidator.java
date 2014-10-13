package de.techdev.trackr.domain.validation;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.DirectFieldAccessor;

import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;

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
        if(beginOrEndIsNull(value)) return true;

        DirectFieldAccessor dfa = new DirectFieldAccessor(value);
        boolean isValid = hasValidLocalDates(dfa) || hasValidLocalTimes(dfa)|| hasValidDates(dfa);
        if (!isValid) {
            messageIsNonValid(context);
        }
        return isValid;
    }

    private boolean beginOrEndIsNull(Object value) {
        DirectFieldAccessor dfa = new DirectFieldAccessor(value);
        return dfa.getPropertyValue(beginFieldName)==null || dfa.getPropertyValue(endFieldName)==null;
    }

    private boolean hasValidLocalDates(DirectFieldAccessor value) {
        if(areBeginAndEndInstanceOf(ChronoLocalDate.class, value)) {
            ChronoLocalDate beginField = (ChronoLocalDate) value.getPropertyValue(beginFieldName);
            ChronoLocalDate endField = (ChronoLocalDate) value.getPropertyValue(endFieldName);
            return !beginField.isAfter(endField);
        };
        return false;
    }

    private boolean hasValidLocalTimes(DirectFieldAccessor value) {
        if(areBeginAndEndInstanceOf(LocalTime.class, value)) {
            LocalTime beginField = (LocalTime) value.getPropertyValue(beginFieldName);
            LocalTime endField = (LocalTime) value.getPropertyValue(endFieldName);
            return !beginField.isAfter(endField);
        };
        return false;
    }

    private boolean hasValidDates(DirectFieldAccessor value) {
        if(areBeginAndEndInstanceOf(Date.class, value)) {
            Date beginField = (Date) value.getPropertyValue(beginFieldName);
            Date endField = (Date) value.getPropertyValue(endFieldName);
            return !beginField.after(endField);
        };
        return false;
    }

    private boolean areBeginAndEndInstanceOf(Class<?> clazz, DirectFieldAccessor value) {
        Class<?> beginFieldType = value.getPropertyType(beginFieldName);
        Class<?> endFieldType = value.getPropertyType(endFieldName);

        return clazz.isAssignableFrom(beginFieldType) && clazz.isAssignableFrom(endFieldType);
    }

    private void messageIsNonValid(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.
        buildConstraintViolationWithTemplate(messageTemplate)
        .addNode(endFieldName).addConstraintViolation();
    }
}
