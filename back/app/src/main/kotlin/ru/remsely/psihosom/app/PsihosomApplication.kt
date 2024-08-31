package ru.remsely.psihosom.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["ru.remsely.psihosom.*"])
class PsihosomApplication

fun main(args: Array<String>) {
    runApplication<PsihosomApplication>(*args)
}
