package de.techdev.trackr.domain.common;

import de.techdev.trackr.domain.employee.vacation.HolidayRepository;
import de.techdev.trackr.util.LocalDateUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static de.techdev.trackr.util.LocalDateUtil.fromDate;
import static de.techdev.trackr.util.LocalDateUtil.fromLocalDate;

/**
 * A trigger that is executed on the last workday in the month.
 *
 * @author Moritz Schulze
 */
@Setter
@Slf4j
public class LastWorkdayDayOfMonthTrigger implements Trigger {

    @Autowired
    private HolidayRepository holidayRepository;

    private FederalState federalState;

    /**
     * Get a list of all holidays for a state and month as {@link java.time.LocalDate}.
     *
     * @param state The federal state
     * @param month The month
     * @return The list of public holidays in the state in the month.
     */
    protected List<LocalDate> getHolidaysForMonth(FederalState state, LocalDate month) {
        LocalDate firstDayOfMonth = month.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
        return holidayRepository
                .findByFederalStateAndDayBetween(state, fromLocalDate(firstDayOfMonth), fromLocalDate(lastDayOfMonth))
                .stream().map(holiday -> fromDate(holiday.getDay()))
                .collect(Collectors.toList());
    }

    /**
     * Get the last day in month that is not a saturday or sunday or public holiday.
     *
     * @param month               The month
     * @param holidaysInThisMonth A list of holidays for the given month
     * @return New date with the last day that is not saturday or sunday or public holiday in the month.
     */
    protected LocalDate lastWeekdayInMonth(LocalDate month, List<LocalDate> holidaysInThisMonth) {
        LocalDate returnDate = month.with(TemporalAdjusters.lastDayOfMonth());
        while (!isWorkday(returnDate, holidaysInThisMonth)) {
            returnDate = returnDate.minusDays(1);
        }
        return returnDate;
    }

    /**
     * Decides whether a date is a work day or not (i.e. not a saturday, sunday or a public holiday).
     *
     * @param date     The date to check
     * @param holidays A list of holidays to use
     * @return true if the day is not a sunday, saturday or in the list of holidays.
     */
    protected boolean isWorkday(LocalDate date, List<LocalDate> holidays) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY
                && !holidays.contains(date);
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        return fromLocalDate(nextExecutionTimeInternal(triggerContext));
    }

    protected LocalDate nextExecutionTimeInternal(TriggerContext triggerContext) {
        LocalDate now = LocalDate.now();
        if (triggerContext.lastScheduledExecutionTime() != null &&
                LocalDateUtil.fromDate(triggerContext.lastScheduledExecutionTime()).getMonth() == now.getMonth()) {
            now = now.plusMonths(1);
        }
        LocalDate nextExecutionTime = lastWeekdayInMonth(now, getHolidaysForMonth(federalState, now));
        log.debug("Trigger for state {} sets next execution date to {}", federalState, nextExecutionTime);
        return nextExecutionTime;
    }
}
