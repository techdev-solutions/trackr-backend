package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestRepository;
import de.techdev.trackr.domain.employee.vacation.VacationRequestService;
import de.techdev.trackr.domain.employee.vacation.VacationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class VacationRequestServiceImpl implements VacationRequestService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public VacationRequest approve(Long vacationRequestId, String supervisorEmail) {
        return setStatusOnVacationRequest(vacationRequestId, supervisorEmail, VacationRequestStatus.APPROVED);
    }

    @Override
    public VacationRequest reject(Long vacationRequestId, String supervisorEmail) {
        return setStatusOnVacationRequest(vacationRequestId, supervisorEmail, VacationRequestStatus.REJECTED);
    }

    protected VacationRequest setStatusOnVacationRequest(Long vacationRequestId, String supervisorEmail, VacationRequestStatus status) {
        VacationRequest vacationRequest = vacationRequestRepository.findOne(vacationRequestId);
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
        }

        return vacationRequest;
    }

}
