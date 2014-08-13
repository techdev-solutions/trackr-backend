package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.domain.common.UuidMapper;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import de.techdev.trackr.domain.employee.vacation.MailApproveService;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestApproveService;
import de.techdev.trackr.domain.employee.vacation.VacationRequestRepository;
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

/**
 * @author Moritz Schulze
 */
@Slf4j
public class DefaultMailApproveService implements MailApproveService {

    @Autowired
    private UuidMapper uuidMapper;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    @Autowired
    private CredentialRepository credentialRepository;

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

    @Override
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

    protected void actualApprove(Long vacationRequestId, String supervisorEmail, String content) {
        // TODO: this really, really should be in an advice or something like that.
        Credential credential = credentialRepository.findByEmail(supervisorEmail);
        Authentication token = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return credential.getAuthorities();
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
                return credential;
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
                return credential.getEmail();
            }
        };
        SecurityContextHolder.getContext().setAuthentication(token);

        VacationRequest vacationRequest = vacationRequestRepository.findOne(vacationRequestId);

        if (vacationRequest != null) {
            if (content.toLowerCase().contains("approve")) {
                log.debug("Approving vacation request from mail.");
                vacationRequestApproveService.approve(vacationRequest, supervisorEmail);
            } else if (content.toLowerCase().contains("reject")) {
                log.debug("Rejecting vacation request from mail.");
                vacationRequestApproveService.reject(vacationRequest, supervisorEmail);
            }
        } else {
            log.debug("Did not find a vacation request for id {}.", vacationRequestId);
        }

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
