package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.hasSize;
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
    private EmployeeDataOnDemand employeeDataOnDemand;

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

    @Test
    public void findBySubmissionTimeBefore() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setSubmissionTime(LocalDateUtil.fromLocalDate(LocalDate.now().minusDays(8)));
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        List<VacationRequest> all = vacationRequestRepository.findBySubmissionTimeBeforeAndStatus(LocalDateUtil.fromLocalDate(LocalDate.now().minusDays(7)), VacationRequest.VacationRequestStatus.PENDING);
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByApprovedBetween() throws ParseException {
        Employee employee = employeeDataOnDemand.getRandomObject();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Date start = df.parse("01.10.2014");
        Date end   = df.parse("08.12.2014");

        VacationRequest vr1 = new VacationRequest();
        vr1.setEmployee(employee);
        vr1.setStartDate(start);
        vr1.setEndDate(end);
        vr1.setStatus(VacationRequest.VacationRequestStatus.APPROVED);
        vacationRequestRepository.save(vr1);

        VacationRequest vr2 = new VacationRequest();
        vr2.setEmployee(employee);
        vr2.setStartDate(start);
        vr2.setEndDate(end);
        vr2.setStatus(VacationRequest.VacationRequestStatus.REJECTED);
        vacationRequestRepository.save(vr2);

        List<VacationRequest> all = vacationRequestRepository
                .findByStartDateBetweenOrEndDateBetweenAndStatus(start, end, start, end, VacationRequest.VacationRequestStatus.APPROVED);
        assertThat(all, hasSize(1));
    }
}
