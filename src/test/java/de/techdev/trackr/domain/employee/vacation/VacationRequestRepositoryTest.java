package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.TransactionalIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class VacationRequestRepositoryTest extends TransactionalIntegrationTest {
    
    @Autowired
    private VacationRequestDataOnDemand vacationRequestDataOnDemand;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Before
    public void setUp() throws Exception {
        vacationRequestDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        VacationRequest one = vacationRequestRepository.findOne(vacationRequest.getId());
        assertThat(one.getId(), is(vacationRequest.getId()));
    }

    @Test
    public void all() throws Exception {
        Iterable<VacationRequest> all = vacationRequestRepository.findAll();
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByEmployeeOrderByStartDateAsc() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        List<VacationRequest> all = vacationRequestRepository.findByEmployeeOrderByStartDateAsc(vacationRequest.getEmployee());
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByApprovedOrderBySubmissionTimeAsc() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        List<VacationRequest> all = vacationRequestRepository.findByStatusOrderBySubmissionTimeAsc(vacationRequest.getStatus());
        assertThat(all, isNotEmpty());
    }
}
