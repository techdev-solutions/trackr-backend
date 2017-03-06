package de.techdev.trackr

import org.springframework.boot.ApplicationPid
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
import org.springframework.context.annotation.Primary
import java.io.File
import javax.annotation.PostConstruct
import javax.sql.DataSource

@SpringBootApplication(exclude = arrayOf(MailSenderAutoConfiguration::class))
@ImportResource(value = "classpath:META-INF/mail-integration.xml")
class Trackr {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    fun primaryDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @PostConstruct
    private fun handlePid() {
        val file = File("application.pid")
        ApplicationPid().write(file)
        file.deleteOnExit()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Trackr::class.java, *args)
        }
    }
}
