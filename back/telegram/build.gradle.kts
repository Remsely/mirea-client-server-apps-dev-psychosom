group = Project.groupId
version = Project.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.use_case))
    implementation(project(Modules.monitoring))

    implementation(Libs.spring_boot_starter)
    implementation(Libs.telegrambots_spring_boot_starter)
    implementation(Libs.telegrambots_extensions)

    implementation(Libs.arrow_core)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
