package de.techdev.trackr.domain.employee.vacation;

import javax.mail.Message;

/**
 * @author Moritz Schulze
 */
public interface MailApproveService {
    void approveOrRejectFromMail(Message mail);
}
