import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.jetbrains.intellij") version "1.17.2"
}

group = "it.orlov.cooklang"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

intellij {
    version.set("IC-241-EAP-SNAPSHOT")
    updateSinceUntilBuild.set(true)
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget = JVM_17
        apiVersion = KOTLIN_1_9
    }
}

tasks {
    buildSearchableOptions {
        enabled = false
    }
}
