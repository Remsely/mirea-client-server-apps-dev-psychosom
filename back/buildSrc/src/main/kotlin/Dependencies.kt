object Libs {
    const val spring_boot_starter = "org.springframework.boot:spring-boot-starter:${LibVersions.spring_boot_starter}"
    const val spring_boot_starter_web = "org.springframework.boot:spring-boot-starter-web:${LibVersions.spring_boot_starter}"
    const val spring_boot_starter_data_jpa = "org.springframework.boot:spring-boot-starter-data-jpa:${LibVersions.spring_boot_starter}"
    const val spring_boot_starter_security = "org.springframework.boot:spring-boot-starter-security:${LibVersions.spring_boot_starter}"
    const val spring_boot_starter_validation = "org.springframework.boot:spring-boot-starter-validation:${LibVersions.spring_boot_starter}"
    const val spring_boot_starter_test = "org.springframework.boot:spring-boot-starter-test:${LibVersions.spring_boot_starter}"

    const val jackson_module_kotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val jetbrains_kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val kotlin_test_junit5 = "org.jetbrains.kotlin:kotlin-test-junit5"

    const val junit_platform_launcher = "org.junit.platform:junit-platform-launcher"

    const val lombock = "org.projectlombok:lombok"
    const val postgresql = "org.postgresql:postgresql:${LibVersions.postgresql}"

    const val arrow_core = "io.arrow-kt:arrow-core:${LibVersions.arrow}"
    const val arrow_optics = "io.arrow-kt:arrow-optics:${LibVersions.arrow}"
    const val arrow_optics_ksp = "io.arrow-kt:arrow-optics-ksp-plugin:${LibVersions.arrow}"
    const val kotest_assertions_arrow = "io.kotest.extensions:kotest-assertions-arrow:${LibVersions.kotest_assertions_arrow}"
}

object LibVersions {
    const val arrow = "1.2.4"
    const val kotest_assertions_arrow = "1.4.0"
    const val spring_boot_starter = "3.3.2"
    const val postgresql = "42.7.1"
}

object Plugins {
    const val kotlin_jvm = "org.jetbrains.kotlin.jvm"
    const val kotlin_spring = "org.jetbrains.kotlin.plugin.spring"
    const val spring_boot = "org.springframework.boot"
    const val spring_dependency_management = "io.spring.dependency-management"
    const val kotlin_jpa = "org.jetbrains.kotlin.plugin.jpa"
    const val ksp = "com.google.devtools.ksp"
}

object PluginVersions {
    const val kotlin_jvm = "2.0.10"
    const val kotlin_spring = "1.9.24"
    const val spring_boot = "3.3.2"
    const val spring_dependency_management = "1.1.6"
    const val kotlin_jpa = "1.9.24"
    const val ksp = "2.0.10-1.0.24"
}
