plugins {
	id(Plugins.kotlin_jvm) version PluginVersions.kotlin_jvm
	id(Plugins.kotlin_spring)version PluginVersions.kotlin_spring
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

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	mainClass.set(Project.main_class)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
