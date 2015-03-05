package de.techdev.trackr.domain.employee.vacation;

import de.techdev.test.TransactionalIntegrationTest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.hasSize;
import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("repositoryTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VacationRequestRepositoryTest extends TransactionalIntegrationTest {
    
    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Test
    public void findBySubmissionTimeBefore() throws Exception {
        List<VacationRequest> all = vacationRequestRepository
                .findBySubmissionTimeBeforeAndStatus(LocalDateUtil.fromLocalDate(LocalDate.of(2014, 10, 16)), VacationRequest.VacationRequestStatus.PENDING);
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByApprovedBetween() {
        Date start = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 10, 1));
        Date end   = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 12, 8));

        List<VacationRequest> all = vacationRequestRepository
                .findByStartDateBetweenOrEndDateBetweenAndStatus(start, end, start, end, VacationRequest.VacationRequestStatus.APPROVED);
        assertThat(all, hasSize(1));
    }
}
