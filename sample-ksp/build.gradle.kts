plugins {
    id("com.google.devtools.ksp")
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    buildToolsVersion = "33.0.0"

    defaultConfig {
        applicationId = "ru.maluginp.ksp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
    ksp(project(":ksp-processor"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
}

ksp {
    arg("option1", "value1")
    arg("option2", "value2")
}
