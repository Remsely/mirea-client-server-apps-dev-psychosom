package ru.remsely.psyhosom.app.config.web

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    private val fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS")

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder
                .deserializers(LocalDateTimeDeserializer(fmt))
                .serializers(LocalDateTimeSerializer(fmt))
        }
}
