package de.techdev.trackr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
@ImportResource(value = "classpath:META-INF/mail-integration.xml")
public class Trackr {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Trackr.class, args);
    }
}
