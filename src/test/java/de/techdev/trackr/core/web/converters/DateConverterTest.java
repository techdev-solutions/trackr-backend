package de.techdev.trackr.core.web.converters;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.echocat.jomon.testing.BaseMatchers.isNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class DateConverterTest {

    private DateConverter dateConverter;

    @Before
    public void setUp() throws Exception {
        dateConverter = new DateConverter();
    }

    @Test
    public void convert10() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expected = sdf.parse("2014-01-01");
        Date date = dateConverter.convert("2014-01-01");
        assertThat(date, is(expected));
    }

    @Test
    public void convert19() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expected = sdf.parse("2014-01-01 13:00:00");
        Date date = dateConverter.convert("2014-01-01 13:00:00");
        assertThat(date, is(expected));
    }

    @Test
    public void convertNull() throws Exception {
        Date date = dateConverter.convert(null);
        assertThat(date, isNull());
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertWrongLength() throws Exception {
        dateConverter.convert("2014-01");
    }
}
