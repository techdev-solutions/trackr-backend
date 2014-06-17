package de.techdev.trackr.core.mail.support;

import de.techdev.trackr.core.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * Puts the mail message into a queue to be sent by Spring Integration.
 * @author Moritz Schulze
 */
public class AsyncMailService implements MailService {

    @Autowired
    @Qualifier("mailSendChannel")
    private MessageChannel mailChannel;

    @Override
    public void sendMail(SimpleMailMessage mailMessage) {
        Message<MailMessage> asyncMessage = new GenericMessage<>(mailMessage);
        mailChannel.send(asyncMessage);
    }
}
