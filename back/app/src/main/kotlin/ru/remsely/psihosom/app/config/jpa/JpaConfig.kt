package ru.remsely.psihosom.app.config.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("ru.remsely.psihosom.db.repository")
@EntityScan("ru.remsely.psihosom.db.entity")
class JpaConfig {
}