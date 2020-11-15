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

tasks.register<Exec>("spectralMakeExecutable") {
    description = "Ensures the application can run."
    group = "apiSpec"

    commandLine("chmod", "700", "$rootDir/spectral")
}

tasks.register<Download>("spectralDownload") {
    description = "Downloads Spectral."
    group = "apiSpec"

    val currentOs = DefaultNativePlatform.getCurrentOperatingSystem()

    val desiredApp = if (currentOs.isMacOsX) "spectral-macos" else "spectral-linux"
    println("${currentOs.displayName} detected, dowloading $desiredApp.")

    src("https://github.com/stoplightio/spectral/releases/download/v5.7.1/$desiredApp")
    dest(File(rootDir, "spectral"))
    overwrite(false)

    finalizedBy("spectralMakeExecutable")
}

tasks.register<Exec>("apiStyleCheck") {
    description = "Checks if HTTP API follows basic rules."
    group = "apiSpec"

    dependsOn(
        tasks.findByName("spectralDownload"),
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
    finalizedBy("apiStyleCheck")
}
