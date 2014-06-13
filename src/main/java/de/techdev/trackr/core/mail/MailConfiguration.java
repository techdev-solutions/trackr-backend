package de.techdev.trackr.core.mail;

import de.techdev.trackr.core.mail.support.JavaMailService;
import de.techdev.trackr.core.mail.support.NoOpMailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiAccessor;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author Moritz Schulze
 */
@Configuration
public class MailConfiguration {

    @Bean
    @Profile("prod")
    public MailService javaMailService() {
        return new JavaMailService();
    }

    @Bean
    @Profile({"dev", "qs"})
    public MailService noOpMailService() {
        return new NoOpMailService();
    }

    @Bean
    @Profile("prod")
    public Properties mailProperties() {
        Properties mailProperties = new Properties();
        JndiAccessor jndiAccessor = new JndiAccessor();
        JndiTemplate jndiTemplate = jndiAccessor.getJndiTemplate();
        String[] propertyNames = {"mail.smtp.host", "mail.smtp.port", "mail.smtp.auth", "mail.smtp.user",
                "mail.smtp.password", "mail.smtp.starttls.enable", "mail.transport.protocol", "mail.smtp.socketFactory.class"};
        try {
            for (String propertyName : propertyNames) {
                Object propertyValue = jndiTemplate.lookup("java:comp/env/" + propertyName);
                mailProperties.put(propertyName, propertyValue);
            }
        } catch (NamingException e) {
            throw new IllegalStateException("Cannot read mail properties from tomcat environemt in context.xml", e);
        }
        return mailProperties;
    }

    @Bean
    @Profile("prod")
    public MailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        /* Yes, thank you spring for this */
        javaMailSender.setHost(mailProperties().getProperty("mail.smtp.host"));
        javaMailSender.setPort((Integer)mailProperties().get("mail.smtp.port"));
        javaMailSender.setUsername(mailProperties().getProperty("mail.smtp.user"));
        javaMailSender.setPassword(mailProperties().getProperty("mail.smtp.password"));

        javaMailSender.setJavaMailProperties(mailProperties());
        return javaMailSender;
    }
}