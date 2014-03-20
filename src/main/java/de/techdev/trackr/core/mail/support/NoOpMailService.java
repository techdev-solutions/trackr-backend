package de.techdev.trackr.core.mail.support;

import de.techdev.trackr.core.mail.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Moritz Schulze
 */
@Slf4j
public class NoOpMailService implements MailService {

    @Override
    public void sendMail(SimpleMailMessage mailMessage) {
        log.info("Sending mail with subject {} to {}", mailMessage.getSubject(), mailMessage.getTo());
    }
}
