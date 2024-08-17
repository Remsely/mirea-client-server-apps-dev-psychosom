plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "psihosom"

include(":domain")
include(":use-case")
include(":db")
include(":api")
include(":app")
