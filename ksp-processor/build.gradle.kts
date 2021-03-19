val kspVersion: String by project

plugins {
    kotlin("jvm")
}

group = "com.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))

    implementation("com.squareup:javapoet:1.13.0")
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.7.10")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.4.9")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

