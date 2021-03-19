// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.31")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.4.30-1.0.0-alpha02")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}