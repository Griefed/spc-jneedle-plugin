import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("serverpackcreator.kotlin-conventions")
    kotlin("kapt")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

val pluginClass = "de.griefed.plugin.jneedle.JNeedlePlugin"
val pluginId = "jneedle-plugin"
val pluginName = "jNeedle Plugin"
val pluginDescription = "This plugins integrates KosmX's jNeedle Java malware scanner into ServerPackCreator."
val pluginAuthor = "Griefed"

dependencies {
    annotationProcessor("org.pf4j:pf4j:3.9.0")
    kapt("org.pf4j:pf4j:3.9.0")
    compileOnly("de.griefed.serverpackcreator:serverpackcreator-api-jvm:4.1.5")
    api("dev.kosmx.needle:jneedle:1.0.1")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
}

tasks.processResources {
    filesMatching("plugin.toml") {
        expand(
            "version" to project.version,
            "plugin_id" to pluginId,
            "plugin_name" to pluginName,
            "plugin_description" to pluginDescription,
            "plugin_author" to pluginAuthor,
            "plugin_class" to pluginClass
        )
    }
    copy {
        from(layout.projectDirectory.file("LICENSE"))
        into(layout.projectDirectory.dir("src/main/resources"))
    }
    copy {
        from(layout.projectDirectory.file("README.md"))
        into(layout.projectDirectory.dir("src/main/resources"))
    }
    copy {
        from(layout.projectDirectory.file("CHANGELOG.md"))
        into(layout.projectDirectory.dir("src/main/resources"))
    }
}

tasks.jar {
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
            mapOf(
                "Main-Class" to pluginClass,
                "Description" to pluginDescription,
                "Built-By" to System.getProperty("user.name"),
                "Build-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Date()),
                "Created-By" to "Gradle ${gradle.gradleVersion}",
                "Build-Jdk" to "${System.getProperty("java.version")} (${System.getProperty("java.vendor")} ${
                    System.getProperty(
                        "java.vm.version"
                    )
                })",
                "Build-OS" to "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${System.getProperty("os.version")}",
                "Plugin-Class" to pluginClass,
                "Plugin-Id" to pluginId,
                "Plugin-Name" to pluginName,
                "Plugin-Provider" to pluginAuthor,
                "Plugin-Version" to project.version,
                "Plugin-Description" to pluginDescription
            )
        )
    }
}