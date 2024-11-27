plugins {
    kotlin("jvm")
}

group = "ru.remsely.psyhosom.monitoring"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(Libs.slf2j)
    implementation(Libs.logback)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
