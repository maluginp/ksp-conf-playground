pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        id("com.google.devtools.ksp") version kspVersion
        kotlin("jvm") version kotlinVersion
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "ksp-playground"

include(
    ":sample-ksp",
    ":sample-kapt",
    ":common",
    ":ksp-processor",
    ":kapt-processor",
)
