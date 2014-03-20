package de.techdev.trackr.domain.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.TriggerContext;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
@RunWith(MockitoJUnitRunner.class)
public class LastWorkdayDayOfMonthTriggerTest {

    @InjectMocks
    private LastWorkdayDayOfMonthTrigger lastWorkdayDayOfMonthTrigger;

    @Test
    public void lastDayIsNotWeekend() throws Exception {
        LocalDate march2014 = LocalDate.of(2014, 3, 1);
        LocalDate lastWeekdayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(march2014);
        assertThat(lastWeekdayInMonth.getDayOfWeek(), is(DayOfWeek.MONDAY));
    }

    @Test
    public void lastDayIsSunday() throws Exception {
        LocalDate march2014 = LocalDate.of(2014, 8, 5);
        LocalDate lastWeekdayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(march2014);
        assertThat(lastWeekdayInMonth.getDayOfWeek(), is(DayOfWeek.FRIDAY));
    }

    @Test
    public void lastDayIsSaturday() throws Exception {
        LocalDate march2014 = LocalDate.of(2014, 5, 2);
        LocalDate lastWeekdayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(march2014);
        assertThat(lastWeekdayInMonth.getDayOfWeek(), is(DayOfWeek.FRIDAY));
    }

    @Test
    public void triggerThisMonthIfLastScheduledTimeIsNull() throws Exception {
        LocalDate date = lastWorkdayDayOfMonthTrigger.nextExecutionTimeInternal(
                getTriggerContextWithLastScheduledExecutionTime(null));
        assertThat(date.getMonth(), is(LocalDate.now().getMonth()));
    }

    @Test
    public void dontTriggerTwiceAMonth() throws Exception {
        LocalDate date = lastWorkdayDayOfMonthTrigger.nextExecutionTimeInternal(
                getTriggerContextWithLastScheduledExecutionTime(toDate(LocalDate.now())));
        assertThat(date.getMonth(), is(LocalDate.now().plusMonths(1).getMonth()));
    }

    private Date toDate(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    protected TriggerContext getTriggerContextWithLastScheduledExecutionTime(final Date time) {
        return new TriggerContext() {
            @Override
            public Date lastScheduledExecutionTime() {
                return time;
            }

            @Override
            public Date lastActualExecutionTime() {
                return null;
            }

            @Override
            public Date lastCompletionTime() {
                return null;
            }
        };
    }
}
