package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.common.FederalState;

import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
public class HolidayDataOnDemand extends AbstractDataOnDemand<Holiday> {

    @Override
    public Holiday getNewTransientObject(int i) {
        Holiday holiday = new Holiday();
        holiday.setFederalState(FederalState.values()[i % FederalState.values().length]);
        holiday.setDay( LocalDate.now());
        holiday.setName("holiday_" + i);
        return holiday; 
    }
}
