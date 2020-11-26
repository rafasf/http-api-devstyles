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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.swagger:swagger-annotations:1.5.21")
}
