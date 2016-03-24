package de.techdev.trackr.core.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;
import javax.mail.Session;

/**
 * This configuration class is mostly copied from {@link org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration}.
 *
 * Since we need to expose the {@link #mailSession()} for our {@code mail-integration.xml} to work and the Spring autoconfig
 * class creates a circular dependency (JavaMailSender <-> Session) we have to use the relevant parts ourselves.
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
@Profile("gmail")
public class GMailConfiguration {

    @Autowired
    private MailProperties properties;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        applyProperties(sender);
        return sender;
    }

    private void applyProperties(JavaMailSenderImpl sender) {
        sender.setHost(this.properties.getHost());
        if (this.properties.getPort() != null) {
            sender.setPort(this.properties.getPort());
        }
        sender.setUsername(this.properties.getUsername());
        sender.setPassword(this.properties.getPassword());
        sender.setProtocol(this.properties.getProtocol());
        if (this.properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(this.properties.getDefaultEncoding().name());
        }
        if (!this.properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(asProperties(this.properties.getProperties()));
        }
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    /**
     * We expose the mail session as a bean for the Spring Integration mail receiver.
     */
    @Bean
    public Session mailSession() {
        return mailSender().getSession();
    }

}