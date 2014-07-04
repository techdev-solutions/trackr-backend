package de.techdev.trackr.core.web.converters;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A {@link java.util.Date} converter that supports the formats yyyy-MM-dd and yyyy-MM-dd HH:mm:ss.
 *
 * @author Moritz Schulze
 */
public class DateConverter implements Converter<String, Date> {

    private DateFormat date10;

    private DateFormat date19;

    public DateConverter() {
        date10 = new SimpleDateFormat("yyyy-MM-dd");
        date19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Date convert(String source) {
        if(source == null) {
            return null;
        }
        Date date;
        try {
            Long milliSeconds = Long.valueOf(source);
            return new Date(milliSeconds);
        } catch (NumberFormatException e) {
            //Continue with other formats
        }
        if(source.length() == 10) {
            try {
                date = date10.parse(source);
            } catch (ParseException e) {
                throw new IllegalArgumentException(String.format("%s is not a valid yyyy-MM-dd date.", source));
            }
        } else if(source.length() == 19) {
            try {
                date = date19.parse(source);
            } catch (ParseException e) {
                throw new IllegalArgumentException(String.format("%s is not a valid yyyy-MM-dd HH:mm:ss date.", source));
            }
        } else {
            throw new IllegalArgumentException(String.format("%s is not convertible by this Date converter", source));
        }
        return date;
    }
}
