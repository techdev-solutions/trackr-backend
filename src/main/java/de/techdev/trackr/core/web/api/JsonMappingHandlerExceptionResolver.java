package de.techdev.trackr.core.web.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static java.util.stream.Collectors.reducing;

/**
 * Custom exception handler for exceptions thrown while JSON mapping by Jackson. They appear not to be handled by {@link org.springframework.web.bind.annotation.ControllerAdvice}
 * exception handlers.
 *
 * @author Moritz Schulze
 */
public class JsonMappingHandlerExceptionResolver implements HandlerExceptionResolver {

    protected JsonGeneratorFactory jsonGeneratorFactory;

    public JsonMappingHandlerExceptionResolver() {
        jsonGeneratorFactory = Json.createGeneratorFactory(null);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (HttpMessageNotReadableException.class.isAssignableFrom(ex.getClass()) &&
                ex.getCause() != null && InvalidFormatException.class.isAssignableFrom(ex.getCause().getClass())) {
            Writer outputWriter;
            try {
                outputWriter = response.getWriter();
            } catch (IOException e) {
                throw new IllegalStateException("Could not open response writer", e);
            }
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            writeExceptionAsJsonToOutput((InvalidFormatException) ex.getCause(), outputWriter);
            try {
                outputWriter.flush();
                outputWriter.close();
            } catch (IOException e) {
                throw new IllegalStateException("Could not flush and close response writer", e);
            }
            return new ModelAndView();
        }
        return null;
    }

    /**
     * Writes the information in the exception as a JSON object like this: { "employee.salary": { "defaultMessage": "Cannot construct float from this value"}}
     * to a writer.
     *
     * @param ex     The exception thrown by Jackson
     * @param writer The output writer to write to.
     */
    protected void writeExceptionAsJsonToOutput(InvalidFormatException ex, Writer writer) {
        JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(writer);
        jsonGenerator.writeStartObject()
                     .writeStartArray("errors")
                     .writeStartObject()
                     .write("property", getFieldPath(ex))
                     .write("message", ex.getOriginalMessage())
                .writeEnd() //object in array
                .writeEnd() //array
                .writeEnd().close();
    }

    /**
     * Appends all fieldNames in the path of the exception to a string connected with dots.
     *
     * @param ex The exception containing the path information
     * @return E.g. "employee.firstName"
     */
    private String getFieldPath(InvalidFormatException ex) {
        return ex.getPath().stream().collect(
                reducing(null, //start value
                        JsonMappingException.Reference::getFieldName, //mapping of one Reference
                        (s, s2) -> s == null ? s2 : s + "." + s2) //concatenate two strings, prohibit dot at the start of the result
        );
    }
}
