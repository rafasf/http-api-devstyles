package com.rafasf.httpapi.devstyles

import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.property
import java.io.File

open class ApiStyleCheckTask : AbstractExecTask<ApiStyleCheckTask>(ApiStyleCheckTask::class.java) {
    @get:Internal
    internal val ignoreFailuresProp = project.objects.property<Boolean>()

    @get:Input
    val apiSpec = project.objects.property<String>()

    @get:OutputFile
    val outputFile = project.objects.property<File>()

    init {
        dependsOn(project.tasks.findByName("spectralDownload"))
        outputFile.set(File("${project.buildDir}/test-results/test/TEST-api-style-check.xml"))
    }

    @TaskAction
    override fun exec() {
        outputFile.get().parentFile.mkdirs()
        commandLine(
            "${project.rootDir}/spectral",
            "lint",
            "--ruleset", "${project.rootDir}/.spectral.json",
            "--format", "junit",
            "--output", outputFile.get().path,
            apiSpec.get()
        )

        super.exec()
    }
}
