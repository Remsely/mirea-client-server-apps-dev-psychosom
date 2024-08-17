plugins {
    id(Plugins.kotlin_jvm) version PluginVersions.kotlin_jvm
    id(Plugins.kotlin_spring) version PluginVersions.kotlin_spring
    id(Plugins.spring_boot) version PluginVersions.spring_boot
    id(Plugins.spring_dependency_management) version PluginVersions.spring_dependency_management
}

group = Constants.groupId
version = Constants.version

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.use_case))
    implementation(project(Modules.api))
    implementation(project(Modules.db))

    implementation(Libs.spring_boot_starter)
    implementation(Libs.spring_boot_starter_security)
    implementation(Libs.spring_boot_starter_web)

    implementation(Libs.jackson_module_kotlin)
    implementation(Libs.jetbrains_kotlin_reflect)

    testImplementation(Libs.spring_boot_starter_test)
    testImplementation(Libs.kotlin_test_junit5)
    testRuntimeOnly(Libs.junit_platform_launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
