plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data-core"))

    // Kotlin
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coroutines.core)
    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.reflect)
    implementation(libs.atomicfu)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.core)
    ksp(libs.koin.ksp)

    // SQL Delight
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
}
