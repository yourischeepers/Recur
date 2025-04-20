plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

dependencies {

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
}

