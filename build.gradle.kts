/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 12:27 by Carina The Latest changes made by Carina on 20.02.22, 12:22 All contents of "build.gradle.kts" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.openjfx.javafxplugin") version "0.0.10"
}

description = "A plugin so that you can remotly use a GUI to connect to your server"
group = "me.carina"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    testImplementation(kotlin("test"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.test {
    useJUnitPlatform()
}


javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}

tasks {
    runServer {
        minecraftVersion("1.18")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}


application {
    mainClass.set("me.carina-sophie.KotlinServerMCKt")
}
