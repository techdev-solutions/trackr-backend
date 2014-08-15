package de.techdev.trackr.domain.employee.vacation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VacationRequestScheduledJobsTest {

    @Mock
    private VacationRequestApproveService vacationRequestApproveService;

    @InjectMocks
    private VacationRequestScheduledJobs vacationRequestScheduledJobs;

    @Test
    public void callsTheRightMethod() throws Exception {
        doNothing().when(vacationRequestApproveService).approveSevenDayOldRequests();
        vacationRequestScheduledJobs.approveSevenDaysOldVacationRequests();
        verify(vacationRequestApproveService, times(1)).approveSevenDayOldRequests();
    }
}