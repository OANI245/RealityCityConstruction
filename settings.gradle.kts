pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9"
}

stonecutter {
    create(rootProject) {
        // See https://stonecutter.kikugie.dev/wiki/start/#choosing-minecraft-versions
        versions("1.20.1", "1.21.1", "1.21.8", "1.21.10", "1.21.11")
        versions("26.1", "26.2-snapshot-3").buildscript("build.unobf.gradle.kts")
        vcsVersion = "1.21.1"
    }
}

rootProject.name = "Reality City Construction NEXT"