package de.techdev.trackr.domain.employee.vacation;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class HolidayCalculatorTest {

    private HolidayCalculator holidayCalculator;

    @Before
    public void setUp() throws Exception {
        holidayCalculator = new HolidayCalculator();
    }

    @Test
    public void calculateDifferenceBetweenExcludingHolidaysAndWeekends() throws Exception {
        LocalDate start = LocalDate.of(2014, Month.DECEMBER, 19);
        LocalDate end = LocalDate.of(2014, Month.DECEMBER, 31);
        Integer numberOfDays = holidayCalculator.calculateDifferenceBetweenExcludingHolidaysAndWeekends(start, end, getHolidaysAsDates());
        assertThat(numberOfDays, is(6));
    }

    @Test
    public void isWeekendOrHolidaySaturday() throws Exception {
        LocalDate date = LocalDate.of(2014, Month.MARCH, 15);
        boolean test = holidayCalculator.isWeekendOrHoliday(date, null);
        assertThat(test, isTrue());
    }

    @Test
    public void isWeekendOrHolidaySunday() throws Exception {
        LocalDate date = LocalDate.of(2014, Month.MARCH, 16);
        boolean test = holidayCalculator.isWeekendOrHoliday(date, null);
        assertThat(test, isTrue());
    }

    @Test
    public void isWeekendOrHolidayInList() throws Exception {
        LocalDate date = LocalDate.of(2014, Month.DECEMBER, 25);
        boolean test = holidayCalculator.isWeekendOrHoliday(date, getHolidaysAsDates());
        assertThat(test, isTrue());
    }

    private List<LocalDate> getHolidaysAsDates() {
        LocalDate holiday1 = LocalDate.of(2014, Month.DECEMBER, 25);
        LocalDate holiday2 = LocalDate.of(2014, Month.DECEMBER, 26);
        LocalDate holiday3 = LocalDate.of(2014, Month.OCTOBER, 3);
        return asList(holiday1, holiday2, holiday3);
    }
}
