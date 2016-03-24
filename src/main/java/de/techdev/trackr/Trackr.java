package de.techdev.trackr;

import org.springframework.boot.ApplicationPid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@SpringBootApplication(exclude = MailSenderAutoConfiguration.class)
@ImportResource(value = "classpath:META-INF/mail-integration.xml")
public class Trackr {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @PostConstruct
    private void handlePid() throws IOException {
        File file = new File("application.pid");
        new ApplicationPid().write(file);
        file.deleteOnExit();
    }

    public static void main(String[] args) {
        SpringApplication.run(Trackr.class, args);
    }
}
