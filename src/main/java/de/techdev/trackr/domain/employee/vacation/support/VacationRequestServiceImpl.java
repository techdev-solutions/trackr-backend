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
        VacationRequest vacationRequest = vacationRequestRepository.findOne(vacationRequestId);
        if (!vacationRequest.isApproved()) {
            Employee supervisor = null;
            Credential byEmail = credentialRepository.findByEmail(supervisorEmail);
            if (byEmail != null) {
                supervisor = byEmail.getEmployee();
            }
            vacationRequest.setStatus(VacationRequestStatus.APPROVED);
            vacationRequest.setApprover(supervisor);
            vacationRequest.setApprovalDate(new Date());
            vacationRequest = vacationRequestRepository.save(vacationRequest);
        }

        return vacationRequest;
    }

}
