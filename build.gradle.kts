import org.jetbrains.changelog.Changelog.OutputType.HTML
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij.platform") version "2.9.0"
    id("org.jetbrains.kotlin.jvm") version "2.2.20"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
    id("org.jetbrains.changelog") version "2.2.0"
}

group = "it.orlov.cooklang"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild.set("242")
        }

        changeNotes.set(provider {
            val changes = if ("SNAPSHOT" in "${project.version}") changelog.getUnreleased() else changelog.getLatest()
            changelog.renderItem(changes, HTML)
        })
    }

    publishing {
        token.set(System.getenv("INTELLIJ_PUBLISH_TOKEN"))
    }

    buildSearchableOptions.set(false)
}

changelog {
    groups.set(emptyList())
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget = JVM_21
        apiVersion = KOTLIN_1_9
    }
}

sourceSets {
    main {
        java.srcDir("src/main/gen")
    }
}

tasks {
    generateLexer {
        sourceFile.set(file("src/main/grammars/_CooklangLexer.flex"))
        targetOutputDir.set(file("src/main/gen/it/orlov/cooklang/lexer"))
        purgeOldFiles.set(true)
    }
    generateParser {
        sourceFile.set(file("src/main/grammars/cooklang.bnf"))
        targetRootOutputDir.set(file("src/main/gen"))
        pathToParser.set("it/orlov/cooklang/psi/CooklangParser.java")
        pathToPsiRoot.set("it/orlov/cooklang/psi")
        purgeOldFiles.set(true)
    }
    withType<KotlinCompile> {
        dependsOn(generateLexer, generateParser)
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaUltimate("LATEST-EAP-SNAPSHOT", false)
    }
}
