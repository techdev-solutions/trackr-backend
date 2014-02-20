package de.techdev.trackr.web.api;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Moritz Schulze
 */
@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, FieldError> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, FieldError> errorMessages = new HashMap<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            FieldError fieldError = toFieldError(violation);
            errorMessages.put(fieldError.getField(), fieldError);
        }
        return errorMessages;
    }

    protected FieldError toFieldError(ConstraintViolation<?> violation) {
        String beanName = violation.getRootBeanClass().getSimpleName();
        String path = violation.getPropertyPath().toString();
        String message = violation.getMessage();
        Object invalidValue = violation.getInvalidValue();
        return new FieldError(beanName, path, invalidValue, false, new String[]{}, new Object[]{}, message);
    }

}