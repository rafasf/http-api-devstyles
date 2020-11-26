plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("org.jetbrains.kotlin:kotlin-allopen")

    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.4.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.14.2")
    implementation("io.gitlab.arturbosch.detekt:detekt-formatting:1.14.2")
}
