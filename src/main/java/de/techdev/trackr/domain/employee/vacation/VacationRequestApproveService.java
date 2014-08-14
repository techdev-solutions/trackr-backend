package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.common.UuidMapper;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import de.techdev.trackr.domain.employee.vacation.support.VacationRequestNotifyService;
import de.techdev.trackr.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Slf4j
public class VacationRequestApproveService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private VacationRequestNotifyService vacationRequestNotifyService;

    @Autowired
    private UuidMapper uuidMapper;

    @Transactional
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != #vacationRequest.employee.id")
    public VacationRequest approve(VacationRequest vacationRequest, String supervisorEmail) {
        return setStatusOnVacationRequest(vacationRequest, supervisorEmail, VacationRequest.VacationRequestStatus.APPROVED);
    }

    /**
     * Rejects a vacation request. The vacation request will be fetched from the repository, the rejector by the name given in
     * supervisor email.
     * <p>
     * Will only reject pending vacation requests.
     *
     * @param vacationRequest   The vacation request to reject.
     * @param supervisorEmail   The email address of the employee to use as the approver.
     * @return The approved (or not) vacation request.
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != #vacationRequest.employee.id")
    public VacationRequest reject(VacationRequest vacationRequest, String supervisorEmail) {
        return setStatusOnVacationRequest(vacationRequest, supervisorEmail, VacationRequest.VacationRequestStatus.REJECTED);
    }

    /**
     * Approves all requests that are more than seven days old.
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void approveSevenDayOldRequests() {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        List<VacationRequest> vacationRequests = vacationRequestRepository.findBySubmissionTimeBeforeAndStatus(LocalDateUtil.fromLocalDate(oneWeekAgo), VacationRequest.VacationRequestStatus.PENDING);
        vacationRequests.forEach(vacationRequest -> {
            log.info("Approving more then seven days old vacation request {}", vacationRequest);
            approve(vacationRequest, null);
        });
    }

    protected VacationRequest setStatusOnVacationRequest(VacationRequest vacationRequest, String supervisorEmail, VacationRequest.VacationRequestStatus status) {
        if (vacationRequest.getStatus() == VacationRequest.VacationRequestStatus.PENDING) {
            Employee supervisor = null;
            Credential byEmail = credentialRepository.findByEmail(supervisorEmail);
            if (byEmail != null) {
                supervisor = byEmail.getEmployee();
            }
            vacationRequest.setStatus(status);
            vacationRequest.setApprover(supervisor);
            vacationRequest.setApprovalDate(new Date());
            vacationRequest = vacationRequestRepository.save(vacationRequest);

            vacationRequestNotifyService.sendEmailNotification(vacationRequest);
        }
        uuidMapper.deleteUUID(vacationRequest.getId());
        return vacationRequest;
    }

}
