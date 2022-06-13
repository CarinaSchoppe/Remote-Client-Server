/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
 *  All contents of "build.gradle.kts" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "+"
    id("application")
    id("xyz.jpenilla.run-paper") version "+"
    id("com.github.johnrengelman.shadow") version "+"
    id("org.openjfx.javafxplugin") version "+"
}

description = "A plugin so that you can remotely use a GUI to connect to your server"
group = "me.carina"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    annotationProcessor("org.apache.logging.log4j:log4j-core:+")
    implementation("org.apache.logging.log4j:log4j-core:+")
    implementation("org.apache.logging.log4j:log4j-api:+")
    implementation("com.google.code.gson:gson:+")
    compileOnly("io.papermc.paper:paper-api:+")
    testImplementation(kotlin("test"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


javafx {
    version = "+"
    modules("javafx.controls", "javafx.fxml")

}

tasks {
    runServer {
        minecraftVersion("1.19")
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    withType<KotlinCompile>{
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xuse-k2",
                "-Xjdk-release=17"
            )
            jvmTarget = "17"
            languageVersion = "1.7"
        }
    }
    test {
        useJUnitPlatform()
    }
}




application {
    mainClass.set("me.carinasophie.KotlinServerMCKt")
}
