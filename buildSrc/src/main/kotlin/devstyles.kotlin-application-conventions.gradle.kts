import com.rafasf.httpapi.devstyles.ApiStyleCheckTask
import de.undercouch.gradle.tasks.download.Download
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.*
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

tasks.register<Exec>("spectralMakeExecutable") {
    description = "Ensures the application can run."
    group = "apiSpec"

    commandLine("chmod", "700", "$rootDir/spectral")
}

tasks.register<Download>("spectralDownload") {
    description = "Downloads Spectral."
    group = "apiSpec"

    val currentOs = getCurrentOperatingSystem()

    val desiredApp = if (currentOs.isMacOsX) "spectral-macos" else "spectral-linux"
    println("${currentOs.displayName} detected, dowloading $desiredApp.")

    src("https://github.com/stoplightio/spectral/releases/download/v5.7.1/$desiredApp")
    dest(File(rootDir, "spectral"))
    overwrite(false)

    finalizedBy("spectralMakeExecutable")
}

val apiStyleCheck by tasks.registering(ApiStyleCheckTask::class) {
    apiSpec.set("$projectDir/src/main/resources/openapi.yml")
}

tasks.withType<Test>() { finalizedBy(apiStyleCheck) }
