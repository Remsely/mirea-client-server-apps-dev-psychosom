plugins {
    id(Plugins.ksp) version PluginVersions.ksp
}

group = Project.groupId
version = Project.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(Libs.spring_boot_starter_validation)
    implementation(Libs.arrow_core)
    implementation(Libs.arrow_optics)
    ksp(Libs.arrow_optics_ksp)

    testImplementation(Libs.spring_boot_starter_test)
    testImplementation(Libs.kotlin_test_junit5)
    testImplementation(Libs.kotest_assertions_arrow)
}
