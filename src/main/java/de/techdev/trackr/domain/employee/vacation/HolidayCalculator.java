package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.common.FederalState;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.*;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Moritz Schulze
 */
public class HolidayCalculator {

    @Autowired
    private HolidayRepository holidayRepository;

    public Integer calculateDifferenceBetweenExcludingHolidaysAndWeekends(Date start, Date end, FederalState federalState) {
        List<Holiday> holidays = holidayRepository.findByFederalStateAndDayBetween(federalState, start, end);
        return calculateDifferenceBetweenExcludingHolidaysAndWeekends(from(start), from(end), holidays.stream()
                                                                                                      .map(holiday -> from(holiday.getDay()))
                                                                                                      .collect(toList()));
    }

    protected Integer calculateDifferenceBetweenExcludingHolidaysAndWeekends(LocalDate start, LocalDate end, List<LocalDate> holidays) {
        LocalDate step = start;
        Integer count = 0;
        while(step.isBefore(end)) {
            if(!isWeekendOrHoliday(step, holidays)) {
                count++;
            }
            step = step.plusDays(1);
        }
        return count;
    }

    /**
     * Checks whether a date is a weekend day or in a given list of holidays.
     *
     * @param date The date to check
     * @param holidays A list of dates that are seens as holidays
     * @return True if the date is a sunday or saturday or in the list.
     */
    protected boolean isWeekendOrHoliday(LocalDate date, List<LocalDate> holidays) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || holidays.stream().anyMatch(date::equals);
    }

    /**
     * Converts a {@link java.util.Date} to a {@link java.time.LocalDate} in the system default timezone.
     * @param date The date to convert
     * @return The converted date
     */
    private static LocalDate from(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }
}
