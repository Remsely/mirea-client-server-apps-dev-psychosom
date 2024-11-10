package ru.remsely.psyhosom.app.config.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("ru.remsely.psyhosom.db.repository")
@EntityScan("ru.remsely.psyhosom.db.entity")
class JpaConfig