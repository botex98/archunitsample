import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.cloudflight.autoconfigure-gradle") version "0.5.6"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "io.cloudflight.engineering.archunit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(platform(libs.spring.boot.bom))
    annotationProcessor(platform(libs.spring.boot.bom))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(libs.swagger.annotations)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(libs.tngtech.archunit)
    testImplementation(libs.tngtech.archunit.junit5)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
