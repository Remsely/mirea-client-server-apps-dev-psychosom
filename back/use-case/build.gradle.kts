plugins {
    id(Plugins.kotlin_jvm) version PluginVersions.kotlin_jvm
}

group = Constants.groupId
version = Constants.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.use_case))

    implementation(Libs.arrow_core)

    testImplementation(Libs.kotlin_test_junit5)
    testImplementation(Libs.spring_boot_starter_test)
    testImplementation(Libs.kotest_assertions_arrow)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}