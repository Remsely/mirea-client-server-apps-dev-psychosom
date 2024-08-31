plugins {

}

group = Project.groupId
version = Project.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.use_case))

    implementation(Libs.spring_boot_starter_web)
    implementation(Libs.spring_boot_starter_validation)
    implementation(Libs.jackson_module_kotlin)

    implementation(Libs.arrow_core)

    testImplementation(Libs.spring_boot_starter_test)
    testImplementation(Libs.kotlin_test_junit5)
    testImplementation(Libs.kotest_assertions_arrow)
}
