plugins {
    id("java")
}

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

    implementation(Libs.arrow_core)

    implementation(Libs.aws_sdk)
}

tasks.test {
    useJUnitPlatform()
}
