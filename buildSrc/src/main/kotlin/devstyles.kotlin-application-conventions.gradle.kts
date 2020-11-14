import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("devstyles.kotlin-common-conventions")

    kotlin("plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.3")

    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
