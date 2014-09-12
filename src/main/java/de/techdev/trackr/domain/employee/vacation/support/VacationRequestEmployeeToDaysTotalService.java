package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.domain.employee.vacation.HolidayCalculator;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author Moritz Schulze
 */
public class VacationRequestEmployeeToDaysTotalService {

    @Autowired
    private HolidayCalculator holidayCalculator;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    /**
     * Groups vacation requests by their employee (the full name to be precise) and sums up all _actual_ vacation days between start and
     * end for all vacation requests of that employee.
     * @param start The start of the period
     * @param end The end of the period
     * @return A map of employee names to days of vacation in between start and end.
     */
    public Map<String, Integer> mapVacationRequestsToTotalDays(LocalDate start, LocalDate end) {
        List<VacationRequest> vacationRequests = vacationRequestRepository
                .findByStartDateBetweenOrEndDateBetweenAndStatus(start, end, start, end, VacationRequest.VacationRequestStatus.APPROVED);
        return mapToEmployeesAndSumUp(vacationRequests, vacationRequest -> getVacationDaysBetween(vacationRequest, start, end));
    }

    /**
     * Groups vacation requests by employee names and adds for every vacation request the numbers returned by the mapper.
     * @param vacationRequests The vacation requests to group
     * @param numberExtractor A function that maps a vacation request to a number.
     * @return A map of the employee names to the sum of numbers returned by the mapper for their vacation requests.
     */
    protected Map<String, Integer> mapToEmployeesAndSumUp(List<VacationRequest> vacationRequests, ToIntFunction<VacationRequest> numberExtractor) {
        return vacationRequests.stream().collect(
                Collectors.groupingBy(
                        vacationRequest -> vacationRequest.getEmployee().getFirstName() + " " + vacationRequest.getEmployee().getLastName(),
                        Collectors.summingInt(numberExtractor)
                )
        );
    }

    /**
     * @return Returns the number of days of the vacation request between start and end that aren't holidays or weekends.
     */
    protected Integer getVacationDaysBetween(VacationRequest vacationRequest, LocalDate start, LocalDate end) {
        return holidayCalculator.calculateDifferenceBetweenExcludingHolidaysAndWeekends(
                // If the start of the vacation request is before the desired period we use the period start
                getMaximum(start, vacationRequest.getStartDate()),
                // If the end of the vacation request is after the desired period we use the period end
                getMinimum(end, vacationRequest.getEndDate()),
                vacationRequest.getEmployee().getFederalState()
        );
    }

    /**
     * The minimum of the two dates. No null checks.
     */
    protected LocalDate getMinimum(LocalDate a, LocalDate b) {
        return a.isBefore(b) ? a : b;
    }

    /**
     * The maximum of the two dates. No null checks.
     */
    protected LocalDate getMaximum(LocalDate a, LocalDate b) {
        return a.isBefore(b) ? b : a;
    }
}
