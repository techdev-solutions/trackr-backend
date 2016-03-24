package de.techdev.trackr.core.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.support.RepositoryConstraintViolationExceptionMessage;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * @author Moritz Schulze
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlers {

    private final MessageSourceAccessor messageSourceAccessor;

    @Autowired
    public ExceptionHandlers(MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    /**
     * This exception handler <i>should</i> handle violations of unique constraints.
     * TODO: JpaSystemException is really broad, we need to check somehow that this only gets invoked for unique constraint violations
     * @param e The exception to handle
     * @return An error message
     */
    @ExceptionHandler(JpaSystemException.class)
    @ResponseBody
    @ResponseStatus(CONFLICT)
    public String handleJpaSystemException(JpaSystemException e) {
        return e.getMostSpecificCause().getMessage();
    }

    /**
     * This is for custom controllers (i.e. not spring-data-rest) that do validation.
     * @param ex The {@link org.springframework.data.rest.core.RepositoryConstraintViolationException} thrown by the controller.
     * @return A map of fieldnames to FieldErrors
     */
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public RepositoryConstraintViolationExceptionMessage handleRepositoryConstraintViolationException(RepositoryConstraintViolationException ex) {
        return new RepositoryConstraintViolationExceptionMessage(ex, messageSourceAccessor);
    }
}