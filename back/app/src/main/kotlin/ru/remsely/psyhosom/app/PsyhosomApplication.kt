package ru.remsely.psyhosom.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["ru.remsely.psyhosom.*"])
class PsyhosomApplication

fun main(args: Array<String>) {
    runApplication<PsyhosomApplication>(*args)
}
