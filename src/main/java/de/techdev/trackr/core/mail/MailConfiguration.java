package de.techdev.trackr.core.mail;

import de.techdev.trackr.core.mail.support.JavaMailService;
import de.techdev.trackr.core.mail.support.NoOpMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Moritz Schulze
 */
@Configuration
public class MailConfiguration {

    @Value("classpath:/mail/gmail.properties")
    private Resource mailPropertiesResource;

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
    public Properties mailProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(mailPropertiesResource);
        try {
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        } catch (IOException e) {
            throw new IllegalStateException("Could not open mail properties file", e);
        }
    }

    @Bean
    @Profile("prod")
    public MailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        /* Yes, thank you spring for this */
        javaMailSender.setHost(mailProperties().getProperty("mail.smtp.host"));
        javaMailSender.setPort(Integer.valueOf(mailProperties().getProperty("mail.smtp.port")));
        javaMailSender.setUsername(mailProperties().getProperty("mail.smtp.user"));
        javaMailSender.setPassword(mailProperties().getProperty("mail.smtp.password"));

        javaMailSender.setJavaMailProperties(mailProperties());
        return javaMailSender;
    }
}