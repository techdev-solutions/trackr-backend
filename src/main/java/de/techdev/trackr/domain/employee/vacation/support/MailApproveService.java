package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.domain.common.UuidMapper;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestApproveService;
import de.techdev.trackr.domain.employee.vacation.VacationRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@Service
@Profile("gmail")
public class MailApproveService {

    @Autowired
    private UuidMapper uuidMapper;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    public void approveOrRejectFromMail(Message mail) {
        log.debug("Got mail.");
        String subject;
        String content;
        String from;
        MessageWrapper message = new MessageWrapper(mail);
        try {
            subject = mail.getSubject();
            content = message.extractContentAsString();
            from = message.getSender();
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Could not extract uuid or content from the mail.", e);
        }
        String uuid = uuidMapper.extractUUIDFromString(subject);

        if (uuid == null) {
            throw new RuntimeException("Could find a UUID in the subject " + subject);
        }
        log.debug("Extracted UUID {} from subject." , uuid);
        Long id = uuidMapper.getIdFromUUID(uuid);

        if (id != null) {
            log.debug("Got ID {} from the mapper.", id);
            actualApprove(id, from, content);
        } else {
            log.debug("Did not find an ID for UUID {}, deleting record.", uuid);
            uuidMapper.deleteUUID(uuid);
        }
    }

    /**
     * Count how often a search string is present in a text.
     * @param text The text to analyze.
     * @param search The search string to find.
     * @return The number of occurrences of search in text, zero if any of the arguments is null.
     */
    protected int containsCount(String text, String search) {
        if (text == null || search == null) {
            return 0;
        }
        int searchLength = search.length();
        int index = text.indexOf(search);
        int count = 0;
        while (index >= 0) {
            count++;
            index = text.indexOf(search, index + searchLength);
        }
        return count;
    }

    /**
     * Analyze the text of a mail and decide if the vacation request should be approved or rejected.
     * If the word "approve" appears more often approve, if the word "reject" appears more often reject. If
     * they appear with the same number, throw an {@link java.lang.IllegalArgumentException}.
     * @param mailContent The content of the mail.
     * @return The status APPROVED when to approve and the status REJECTED when to reject.
     */
    protected VacationRequest.VacationRequestStatus approveOrReject(String mailContent) {
        String mailContentLowerCase = mailContent.toLowerCase();
        int approveCount = containsCount(mailContentLowerCase, "approve");
        int rejectCount = containsCount(mailContentLowerCase, "reject");
        if (approveCount > rejectCount) {
            return VacationRequest.VacationRequestStatus.APPROVED;
        } else if (approveCount < rejectCount) {
            return VacationRequest.VacationRequestStatus.REJECTED;
        } else {
            throw new IllegalStateException("Cannot decide from the text if to approve or reject");
        }
    }

    protected void actualApprove(Long vacationRequestId, String supervisorEmail, String content) {
        VacationRequest vacationRequest = vacationRequestRepository.findOneWithoutSecurity(vacationRequestId);
        if (vacationRequest == null) {
            log.debug("Did not find a vacation request for id {}.", vacationRequestId);
            return;
        }

        if (vacationRequest.getEmployee().getEmail().equals(supervisorEmail)) {
            log.warn("Supervisor {} tried to approve his/her own vacation request via mail.", supervisorEmail);
            return;
        }

        VacationRequest.VacationRequestStatus status = approveOrReject(content);
        if (status == VacationRequest.VacationRequestStatus.APPROVED) {
            vacationRequestApproveService.approve(vacationRequest, supervisorEmail);
        } else if (status == VacationRequest.VacationRequestStatus.REJECTED) {
            vacationRequestApproveService.reject(vacationRequest, supervisorEmail);
        }
    }
}
