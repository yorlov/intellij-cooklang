import org.jetbrains.changelog.Changelog.OutputType.HTML
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.jetbrains.intellij") version "1.17.2"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
    id("org.jetbrains.changelog") version "2.2.0"
}

group = "it.orlov.cooklang"

repositories {
    mavenCentral()
}

intellij {
    version.set("IC-241-EAP-SNAPSHOT")
    updateSinceUntilBuild.set(true)
}

changelog {
    groups.set(emptyList())
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget = JVM_17
        apiVersion = KOTLIN_1_9
    }
}

sourceSets {
    main {
        java.srcDir("src/main/gen")
    }
}

tasks {
    publishPlugin {
        token.set(System.getenv("INTELLIJ_PUBLISH_TOKEN"))
    }

    patchPluginXml {
        changeNotes.set(provider {
            val changes = if ("${project.version}".contains("SNAPSHOT")) changelog.getUnreleased() else changelog.getLatest()
            changelog.renderItem(changes, HTML)
        })
    }

    buildSearchableOptions {
        enabled = false
    }

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
