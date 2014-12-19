package de.techdev.trackr.core.mail.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Mock class to be used by Spring Integration for dev and qs.
 *
 * @author Moritz Schulze
 * @author Alexander Hanschke
 */
@Slf4j
public class NoOpJavaMailSender implements JavaMailSender {

    @Override
    public MimeMessage createMimeMessage() {
        log.debug("Create mime message");
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) {
        log.debug("Create Mime message with stream");
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) {
        log.debug("Send single mime message");
    }

    @Override
    public void send(MimeMessage[] mimeMessages) {
        log.debug("Send multiple mime messages");
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) {
        log.debug("Send mime message preparator");
    }

    @Override
    public void send(MimeMessagePreparator[] mimeMessagePreparators) {
        log.debug("Send multiple mime message preparators");
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) {
        StringBuilder builder = new StringBuilder("Send simple mail message..\n");
        builder
                .append("From:    ").append(simpleMessage.getFrom()).append("\n")
                .append("To:      ").append(Arrays.toString(simpleMessage.getTo())).append("\n")
                .append("Subject: ").append(simpleMessage.getSubject()).append("\n")
                .append("--------------------------------------------").append("\n")
                .append(simpleMessage.getText());

        log.debug(builder.toString());
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) {
        log.debug("Send multiple simple messages");
    }
}
