plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(Deps.javaPoet)
    implementation(project(":common"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.4.30")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.3.6")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

