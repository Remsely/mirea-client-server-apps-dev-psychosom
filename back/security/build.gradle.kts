group = Project.groupId
version = Project.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.use_case))
    implementation(project(Modules.monitoring))

    implementation(Libs.spring_boot_starter_security)
    implementation(Libs.spring_boot_starter_oauth2_resource_server)
    implementation(Libs.spring_tx)

    compileOnly(Libs.jakarta_servlet_api)

    implementation(Libs.arrow_core)
}
