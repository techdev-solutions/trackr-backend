package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.core.security.AuthorityService;
import de.techdev.trackr.domain.common.UuidMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

@Slf4j
public class MailApproveService {

    @Autowired
    private UuidMapper uuidMapper;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * Extract the content of a Mail as a String depending on the content type.
     * text/plain -> directly
     * multipart/mime -> only the text/plain part if it exists, null otherwise
     * multipart -> the body
     * @param mailMessage
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    protected String extractContentAsString(Message mailMessage) throws IOException, MessagingException {
        Object content = mailMessage.getContent();
        if (content instanceof String) {
            return (String) content;
        }
        if(content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                if (multipart.getBodyPart(i).getContentType().startsWith("text/plain")) {
                    return (String)multipart.getBodyPart(i).getContent();
                }
            }
            return null;
        }
        if (content instanceof Multipart) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ((Multipart) content).writeTo(outputStream);
            return new String(outputStream.toByteArray(), "UTF-8");
        }
        throw new IllegalArgumentException("Incompatible message type.");
    }

    /**
     * Extracts the sender of an email if it is an InternetAddress.
     * @param mail
     * @return The address or null.
     * @throws MessagingException
     */
    protected String getSender(Message mail) throws MessagingException {
        String from = null;
        Address[] fromArray = mail.getFrom();
        if (fromArray.length > 0) {
            Address address = mail.getFrom()[0];
            if (address instanceof InternetAddress) {
                from = ((InternetAddress) address).getAddress();
                if (!from.endsWith("techdev.de")) {
                    from = null;
                }
            }
        }
        return from;
    }

    public void approveOrRejectFromMail(Message mail) {
        log.debug("Got mail.");
        String subject;
        String content;
        String from;
        try {
            subject = mail.getSubject();
            content = extractContentAsString(mail);
            from = getSender(mail);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Could not extract uuid or content from the mail.", e);
        }
        String uuid = uuidMapper.extractUUUIDfromString(subject);

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
        // TODO: this really, really should be in an advice or something like that.
        Collection<GrantedAuthority> authorities = authorityService.getByEmployeeMail(supervisorEmail);
        Authentication token = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return supervisorEmail;
            }
        };
        SecurityContextHolder.getContext().setAuthentication(token);

        VacationRequest vacationRequest = vacationRequestRepository.findOne(vacationRequestId);

        if (vacationRequest != null) {
            VacationRequest.VacationRequestStatus status = approveOrReject(content);
            if (status == VacationRequest.VacationRequestStatus.APPROVED) {
                log.debug("Approving vacation request from mail.");
                vacationRequestApproveService.approve(vacationRequest, supervisorEmail);
            } else if (status == VacationRequest.VacationRequestStatus.REJECTED) {
                log.debug("Rejecting vacation request from mail.");
                vacationRequestApproveService.reject(vacationRequest, supervisorEmail);
            }
        } else {
            log.debug("Did not find a vacation request for id {}.", vacationRequestId);
        }

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
