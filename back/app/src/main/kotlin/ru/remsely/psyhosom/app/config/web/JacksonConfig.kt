package ru.remsely.psyhosom.app.config.web

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    private val dateTimeFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS")
    private val dateFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private val timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss")

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder
                .serializers(
                    LocalDateTimeSerializer(dateTimeFmt),
                    LocalDateSerializer(dateFmt),
                    LocalTimeSerializer(timeFmt)
                )
                .deserializers(
                    LocalDateTimeDeserializer(dateTimeFmt),
                    LocalDateDeserializer(dateFmt),
                    LocalTimeDeserializer(timeFmt)
                )
        }
}

