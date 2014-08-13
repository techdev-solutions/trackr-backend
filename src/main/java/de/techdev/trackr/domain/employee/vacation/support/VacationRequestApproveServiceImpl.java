package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.domain.common.UuidMapper;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestApproveService;
import de.techdev.trackr.domain.employee.vacation.VacationRequestRepository;
import de.techdev.trackr.domain.employee.vacation.VacationRequestStatus;
import de.techdev.trackr.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Slf4j
public class VacationRequestApproveServiceImpl implements VacationRequestApproveService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private VacationRequestNotifyService vacationRequestNotifyService;

    @Autowired
    private UuidMapper uuidMapper;

    @Override
    public VacationRequest approve(VacationRequest vacationRequest, String supervisorEmail) {
        return setStatusOnVacationRequest(vacationRequest, supervisorEmail, VacationRequestStatus.APPROVED);
    }

    @Override
    public VacationRequest reject(VacationRequest vacationRequest, String supervisorEmail) {
        return setStatusOnVacationRequest(vacationRequest, supervisorEmail, VacationRequestStatus.REJECTED);
    }

    @Override
    public void approveSevenDayOldRequests() {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        List<VacationRequest> vacationRequests = vacationRequestRepository.findBySubmissionTimeBeforeAndStatus(LocalDateUtil.fromLocalDate(oneWeekAgo), VacationRequestStatus.PENDING);
        vacationRequests.forEach(vacationRequest -> {
            log.info("Approving more then seven days old vacation request {}", vacationRequest);
            approve(vacationRequest, null);
        });
    }

    protected VacationRequest setStatusOnVacationRequest(VacationRequest vacationRequest, String supervisorEmail, VacationRequestStatus status) {
        if (vacationRequest.getStatus() == VacationRequestStatus.PENDING) {
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
