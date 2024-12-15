plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "psyhosom"

include(":domain")
include(":use-case")
include(":db")
include(":api")
include(":app")
include(":security")
include(":monitoring")
include(":telegram")
include(":scheduled")
