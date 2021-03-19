plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(project(":common"))

    implementation("com.squareup:javapoet:1.13.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.4.30")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.9")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

