package de.techdev.trackr.domain.common;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * A trigger that is executed on the last workday in the month.
 *
 * @author Moritz Schulze
 */
public class LastWorkdayDayOfMonthTrigger implements Trigger {

    protected LocalDate lastWeekdayInMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
    }

    //TODO: holidays
    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        return toDate(lastWeekdayInMonth());
    }

    private Date toDate(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
