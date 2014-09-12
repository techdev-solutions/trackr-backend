package de.techdev.trackr.domain.scheduling;

import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.vacation.Holiday;
import de.techdev.trackr.domain.employee.vacation.HolidayRepository;
import de.techdev.trackr.domain.scheduling.LastWorkdayDayOfMonthTrigger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.TriggerContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static de.techdev.trackr.util.LocalDateUtil.fromLocalDate;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Moritz Schulze
 */
@RunWith(MockitoJUnitRunner.class)
public class LastWorkdayDayOfMonthTriggerTest {

    @InjectMocks
    private LastWorkdayDayOfMonthTrigger lastWorkdayDayOfMonthTrigger = new LastWorkdayDayOfMonthTrigger();

    @Mock
    private HolidayRepository holidayRepository;

    private List<LocalDate> holidays;

    @Before
    public void setUp() throws Exception {
        lastWorkdayDayOfMonthTrigger.setFederalState(FederalState.BERLIN);
        Holiday holiday = new Holiday();
        holiday.setDay(LocalDate.of(2014, 5, 1));
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(holiday);
        when(holidayRepository.findByFederalStateAndDayBetween(Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(holidayList);

        holidays = new ArrayList<>();
        holidays.add(holiday.getDay());
    }

    @Test
    public void lastDayIsNotWeekend() throws Exception {
        LocalDate march2014 = LocalDate.of(2014, 3, 1);
        LocalDate lastWeekdayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(march2014, holidays);
        assertThat(lastWeekdayInMonth.getDayOfWeek(), is(DayOfWeek.MONDAY));
    }

    @Test
    public void lastDayIsSunday() throws Exception {
        LocalDate march2014 = LocalDate.of(2014, 8, 5);
        LocalDate lastWeekdayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(march2014, holidays);
        assertThat(lastWeekdayInMonth.getDayOfWeek(), is(DayOfWeek.FRIDAY));
    }

    @Test
    public void lastDayIsSaturday() throws Exception {
        LocalDate march2014 = LocalDate.of(2014, 5, 2);
        LocalDate lastWeekdayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(march2014, holidays);
        assertThat(lastWeekdayInMonth.getDayOfWeek(), is(DayOfWeek.FRIDAY));
    }

    @Test
    public void lastDayIsMondayAndHoliday() throws Exception {
        LocalDate june2014 = LocalDate.of(2014, 6, 1);
        List<LocalDate> holidays = asList(LocalDate.of(2014, 6, 30));
        LocalDate lastWeekDayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(june2014, holidays);
        assertThat(lastWeekDayInMonth.getDayOfWeek(), is(DayOfWeek.FRIDAY));
        assertThat(lastWeekDayInMonth.getDayOfMonth(), is(27));
    }

    @Test
    public void lastDayIsMondayAndHolidayAndTheFridayIsAHolidayToo() throws Exception {
        LocalDate june2014 = LocalDate.of(2014, 6, 1);
        List<LocalDate> holidays = asList(LocalDate.of(2014, 6, 27), LocalDate.of(2014, 6, 30));
        LocalDate lastWeekDayInMonth = lastWorkdayDayOfMonthTrigger.lastWeekdayInMonth(june2014, holidays);
        assertThat(lastWeekDayInMonth.getDayOfWeek(), is(DayOfWeek.THURSDAY));
        assertThat(lastWeekDayInMonth.getDayOfMonth(), is(26));
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
                getTriggerContextWithLastScheduledExecutionTime(fromLocalDate(LocalDate.now())));
        assertThat(date.getMonth(), is(LocalDate.now().plusMonths(1).getMonth()));
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
