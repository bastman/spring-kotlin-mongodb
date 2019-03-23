package com.example

//import com.example.api.ApiConfig
import com.example.api.ApiConfig
import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication
class RestApiApplication(private val apiConfig: ApiConfig) : ApplicationListener<ApplicationReadyEvent> {
    @PostConstruct
    fun starting() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        logger.info { "===== SPRING BOOT APP STARTING: ${apiConfig.title} ====" }
    }

    override fun onApplicationEvent(contextRefreshedEvent: ApplicationReadyEvent) {
        logger.info("===== SPRING BOOT APP STARTED: ${apiConfig.title} ======")
        System.gc()
    }

    companion object : KLogging()
}
