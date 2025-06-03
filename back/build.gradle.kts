import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id(Plugins.kotlin_jvm) version PluginVersions.kotlin_jvm
    id(Plugins.kotlin_spring) version PluginVersions.kotlin_spring
    id(Plugins.spring_boot) version PluginVersions.spring_boot
    id(Plugins.spring_dependency_management) version PluginVersions.spring_dependency_management
    id(Plugins.kotlin_jpa) version PluginVersions.kotlin_jpa
    id(Plugins.ksp) version PluginVersions.ksp
}

group = Project.groupId
version = Project.version

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations.all {
    resolutionStrategy {
        preferProjectModules()
    }
}

repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin(Plugins.kotlin_jvm)
        plugin(Plugins.kotlin_spring)
    }

    repositories {
        mavenCentral()
    }

    tasks {
        withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs = listOf("-Xjvm-default=all-compatibility")
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }

        withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:all")
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}

tasks.withType<BootJar> {
    mainClass.set(Project.main_class)
}
