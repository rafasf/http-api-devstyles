import com.rafasf.httpapi.devstyles.ApiStyleCheckTask

plugins {
    id("devstyles.kotlin-application-conventions")
    kotlin("kapt")

    id("com.github.johnrengelman.processes") version "0.5.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.3.0"
}

val springDocVersion = "1.5.0"
val arrowVersion = "0.11.0"

dependencies {
    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:$springDocVersion")
//    implementation("org.springdoc:springdoc-openapi-security:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")

    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    kapt("io.arrow-kt:arrow-meta:$arrowVersion")

    testImplementation("au.com.dius.pact.consumer:junit5:4.1.0")
    testImplementation("au.com.dius.pact.provider:junit5:4.1.0")
    testImplementation("au.com.dius.pact.provider:junit5spring:4.1.0")
}

tasks.named<ApiStyleCheckTask>("apiStyleCheck").configure {
    dependsOn(tasks.named("generateOpenApiDocs"))
    apiSpec.set("$buildDir/openapi.json")
}
