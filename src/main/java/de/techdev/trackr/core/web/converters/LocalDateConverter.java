package de.techdev.trackr.core.web.converters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.core.convert.converter.Converter;

/**
 * A {@link java.util.Date} converter that supports the formats yyyy-MM-dd and yyyy-MM-dd HH:mm:ss.
 *
 * @author Moritz Schulze
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    private DateTimeFormatter date10;

    private DateTimeFormatter date19;

    public LocalDateConverter() {
        date10 =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date19 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public LocalDate convert(String source) {
        if(source == null) {
            return null;
        }
        LocalDate date;
        try {
            Long milliSeconds = Long.valueOf(source);
            Instant instant = Instant.ofEpochMilli(milliSeconds);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        } catch (NumberFormatException e) {
            //Continue with other formats
        }
        if(source.length() == 10) {
            try {
                date = LocalDate.parse(source,date10);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(String.format("%s is not a valid yyyy-MM-dd date.", source));
            }
        } else if(source.length() == 19) {
            try {
                date = LocalDate.parse(source,date19);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(String.format("%s is not a valid yyyy-MM-dd HH:mm:ss date.", source));
            }
        } else {
            throw new IllegalArgumentException(String.format("%s is not convertible by this Date converter", source));
        }
        return date;
    }
}
