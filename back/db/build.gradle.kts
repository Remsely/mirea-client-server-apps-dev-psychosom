plugins {
    id(Plugins.kotlin_jpa) version PluginVersions.kotlin_jpa
    id(Plugins.flyway) version PluginVersions.flyway
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

    implementation(Libs.spring_boot_starter_data_jpa)

    implementation(Libs.arrow_core)

    implementation(Libs.postgresql)
    implementation(Libs.flyway_core)
    implementation(Libs.flyway_database_postgresql)

    implementation(Libs.jetbrains_kotlin_reflect)

    testImplementation(Libs.spring_boot_starter_test)
    testImplementation(Libs.kotlin_test_junit5)
    testImplementation(Libs.kotest_assertions_arrow)
}
