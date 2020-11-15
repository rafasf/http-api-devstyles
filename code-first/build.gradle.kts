import de.undercouch.gradle.tasks.download.Download
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    id("devstyles.kotlin-application-conventions")

    id("com.github.johnrengelman.processes") version "0.5.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.3.0"
}

val springDocVersion = "1.5.0"

dependencies {
    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:$springDocVersion")
//    implementation("org.springdoc:springdoc-openapi-security:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")
}

tasks.register<Download>("spectral") {
    val currentOs = DefaultNativePlatform.getCurrentOperatingSystem()

    val desiredApp = if (currentOs.isMacOsX) "spectral-macos" else "spectral-linux"
    println("${currentOs.displayName} detected, dowloading $desiredApp.")

    val endLocation = File(".", "spectral")
    endLocation.setExecutable(true)

    src("https://github.com/stoplightio/spectral/releases/download/v5.7.1/$desiredApp")
    dest(endLocation)
    overwrite(false)
}

tasks.register<Exec>("api-spec") {
    dependsOn(
        tasks.findByName("spectral"),
        tasks.findByName("generateOpenApiDocs")
    )

    workingDir(projectDir)
    commandLine(
        "../spectral",
        "lint",
        "--ruleset", "../.spectral.json",
        "--format", "junit",
        "--output", "$buildDir/test-results/test/TEST-api-style-check.xml",
        "$buildDir/openapi.json"
    )
}

tasks.withType<Test>() {
    finalizedBy("api-spec")
}
