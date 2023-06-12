@file:Suppress("UnstableApiUsage")

import de.griefed.common.gradle.constant.JDK_VERSION
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.text.SimpleDateFormat
import java.util.*

repositories {
    mavenCentral()
}

plugins {
    java
    `java-library`
    idea
}

java {
    // Auto JDK setup
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JDK_VERSION))
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events = setOf(
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.compileJava {
    // See: https://docs.oracle.com/en/java/javase/12/tools/javac.html
    @Suppress("SpellCheckingInspection")
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all", // Enables all recommended warnings.
        )
    )
    options.encoding = "UTF-8"
}

tasks.getByName("sourcesJar",Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.clean {
    doFirst {
        cleanup()
    }
}

tasks.test {
    doFirst {
        cleanup()
    }
}

fun cleanup() {
    val tests = File(projectDir,"tests").absoluteFile
    mkdir(tests.absolutePath)
    val gitkeep = File(tests,".gitkeep").absoluteFile
    if (!gitkeep.exists()) {
        File(tests,".gitkeep").writeText("Hi")
    }
    projectDir.resolve("tests")
        .listFiles()
        .filter { !it.name.endsWith("gitkeep") }
        .forEach {
            it.deleteRecursively()
        }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Built-By" to System.getProperty("user.name"),
                "Build-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Date()),
                "Created-By" to "Gradle ${gradle.gradleVersion}",
                "Build-Jdk" to "${System.getProperty("java.version")} (${System.getProperty("java.vendor")} ${
                    System.getProperty("java.vm.version")
                })",
                "Build-OS" to "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${
                    System.getProperty("os.version")
                }",
                "Implementation-Vendor" to "Griefed",
                "Implementation-Version" to project.version,
                "Implementation-Title" to project.name
            )
        )
    }
}
