import de.undercouch.gradle.tasks.download.Download
import org.apache.tools.ant.taskdefs.condition.Os.*

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
    val endLocation = File(".", "spectral")
    endLocation.setExecutable(true)

    val desiredApp = if (isFamily(FAMILY_MAC)) "spectral-macos" else "spectral-linux"

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
        "$buildDir/openapi.json"
    )
}

tasks.withType<Test>() {
    finalizedBy("api-spec")
}
