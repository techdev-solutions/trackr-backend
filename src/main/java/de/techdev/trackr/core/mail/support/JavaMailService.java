package de.techdev.trackr.core.mail.support;

import de.techdev.trackr.core.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Moritz Schulze
 */
public class JavaMailService implements MailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMail(SimpleMailMessage mailMessage) {

    }
}
