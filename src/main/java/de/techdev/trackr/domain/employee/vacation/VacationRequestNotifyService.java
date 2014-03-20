package de.techdev.trackr.domain.employee.vacation;

/**
 * @author Moritz Schulze
 */
public interface VacationRequestNotifyService {

    /**
     * Send an email notification to the employee containing the status and the approver.
     */
    void sendEmailNotification(VacationRequest request);

}
