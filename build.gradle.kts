import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
    java
    `java-library`
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.openjfx.javafxplugin") version "0.0.10"
}

description = "A plugin so that you can remotly use a GUI to connect to your server"
group = "me.carina"
version = "1.0.0"

repositories {
    gradlePluginPortal()
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
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "me.carina.KotlinServerMC"
    apiVersion = "1.18"
    authors = listOf("Carina-Sophie Schoppe")
}

application {
    mainClass.set("me.carina-sophie.KotlinServerMCKt")
}
