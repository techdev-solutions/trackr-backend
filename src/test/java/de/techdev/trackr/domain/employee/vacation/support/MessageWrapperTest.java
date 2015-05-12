package de.techdev.trackr.domain.employee.vacation.support;

import org.junit.Test;

import javax.mail.Session;
import javax.mail.internet.*;
import java.util.Properties;

import static org.echocat.jomon.testing.BaseMatchers.isNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MessageWrapperTest {

    @Test
    public void getSender() throws Exception {
        MimeMessage mail = new MimeMessage(Session.getDefaultInstance(new Properties()));
        mail.setFrom(new InternetAddress("test@techdev.de"));
        String sender = new MessageWrapper(mail).getSender();
        assertThat(sender, is("test@techdev.de"));
    }

    @Test
    public void getSenderReturnsNullWhenNotTechdev() throws Exception {
        MimeMessage mail = new MimeMessage(Session.getDefaultInstance(new Properties()));
        mail.setFrom(new InternetAddress("test@gmail.de"));
        String sender = new MessageWrapper(mail).getSender();
        assertThat(sender, isNull());
    }

    @Test
    public void getBodyPlaintext() throws Exception {
        MimeMessage mail = new MimeMessage(Session.getDefaultInstance(new Properties()));
        mail.setContent("Test", "text/plain");
        String body = new MessageWrapper(mail).extractContentAsString();
        assertThat(body, is("Test"));
    }

    @Test
    public void getBodyMimeMultipart() throws Exception {
        MimeMessage mail = new MimeMessage(Session.getDefaultInstance(new Properties()));
        MimeMultipart mimeMultipart = new MimeMultipart();

        MimeBodyPart plainTextPart = new MimeBodyPart(new InternetHeaders(), "Test".getBytes());
        plainTextPart.setContent("Test", "text/plain");
        mimeMultipart.addBodyPart(plainTextPart);

        MimeBodyPart otherPart = new MimeBodyPart(new InternetHeaders(), "Other".getBytes());
        otherPart.setContent("<b>Other</b>", "text/html");
        mimeMultipart.addBodyPart(otherPart);

        mail.setContent(mimeMultipart);
        String body = new MessageWrapper(mail).extractContentAsString();
        assertThat(body, is("Test"));
    }

}