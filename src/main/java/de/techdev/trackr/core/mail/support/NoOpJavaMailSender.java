package de.techdev.trackr.core.mail.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

/**
 * Mock class to be used by Spring Integration for dev and qs.
 *
 * @author Moritz Schulze
 */
@Slf4j
public class NoOpJavaMailSender implements JavaMailSender {

    @Override
    public MimeMessage createMimeMessage() {
        log.debug("Create mime message");
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        log.debug("Create Mime message with stream");
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        log.debug("Send single mime message");
    }

    @Override
    public void send(MimeMessage[] mimeMessages) throws MailException {
        log.debug("Send multiple mime messages");
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        log.debug("Send mime message preparator");
    }

    @Override
    public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
        log.debug("Send multiple mime message preparators");
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        log.debug("Send simple mail message");
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) throws MailException {
        log.debug("Send multiple simple messages");
    }
}
