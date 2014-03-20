package de.techdev.trackr.core.mail;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author Moritz Schulze
 */
public interface MailService {

    void sendMail(SimpleMailMessage mailMessage);

}
