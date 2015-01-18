package de.techdev.trackr.core.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Session;

@Configuration
@Profile("gmail")
public class GMailConfiguration {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * We expose the mail session as a bean for the Spring Integration mail receiver.
     */
    @Bean
    public Session mailSession() {
        if (JavaMailSenderImpl.class.isAssignableFrom(javaMailSender.getClass())) {
            return ((JavaMailSenderImpl) javaMailSender).getSession();
        } else {
            throw new IllegalStateException("Mail sender must be a JavaMailSenderImpl");
        }
    }

}