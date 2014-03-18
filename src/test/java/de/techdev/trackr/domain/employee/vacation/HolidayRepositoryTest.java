package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.TransactionalIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class HolidayRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private HolidayDataOnDemand holidayDataOnDemand;

    @Autowired
    private HolidayRepository holidayRepository;

    @Before
    public void setUp() throws Exception {
        holidayDataOnDemand.init();
    }

    @Test
    public void findByFederalStateAndDayBetween() throws Exception {
        Holiday holiday = holidayDataOnDemand.getRandomObject();
        List<Holiday> holidays = holidayRepository.findByFederalStateAndDayBetween(holiday.getFederalState(), holiday.getDay(), holiday.getDay());
        assertThat(holidays, isNotEmpty());
    }
}
