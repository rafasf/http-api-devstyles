import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("devstyles.kotlin-application-conventions")

    id("org.openapi.generator") version "5.0.0-beta2"
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/openapi.yml".toString())
    outputDir.set("$buildDir/generated".toString())

    apiPackage.set("com.rafasf.httpapi.devstyles.specfirst.http")
    modelPackage.set("com.rafasf.httpapi.devstyles.specfirst.http.representations")
    skipOverwrite.set(false)

    globalProperties.apply {
        put("apis", "")
        put("models", "")
    }

    configOptions.apply {
        put("enumPropertyNaming", "PascalCase")
        put("serialization", "jackson")
        put("dateLibrary", "java8")
        put("useBeanValidation", "true")
        put("interfaceOnly", "true")
        put("useTags", "true")
        put("swaggerAnnotations", "true")
        put("title", "$project.name")
    }
}

sourceSets {
    val main by getting
    main.java.srcDirs("$buildDir/generated/src/main/kotlin")
}

tasks.withType<KotlinCompile> {
    dependsOn("openApiGenerate")
}

tasks.register<Exec>("spectralMakeExecutable") {
    description = "Ensures the application can run."
    group = "apiSpec"

    commandLine("chmod", "700", "$rootDir/spectral")
}

tasks.register<de.undercouch.gradle.tasks.download.Download>("spectralDownload") {
    description = "Downloads Spectral."
    group = "apiSpec"

    val currentOs = org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem()

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

    dependsOn(tasks.findByName("spectralDownload"))

    workingDir(projectDir)
    commandLine(
        "../spectral",
        "lint",
        "--ruleset", "../.spectral.json",
        "--format", "junit",
        "--output", "$buildDir/test-results/test/TEST-api-style-check.xml",
        "$projectDir/src/main/resources/openapi.json"
    )
}

tasks.withType<Test>() {
    finalizedBy("apiStyleCheck")
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.swagger:swagger-annotations:1.5.21")
}
