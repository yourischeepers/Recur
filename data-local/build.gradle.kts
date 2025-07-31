plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
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

android {
    namespace = "me.partypronl.recur"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("me.partypronl.recur.data.local")
            schemaOutputDirectory.set(rootProject.rootDir)
            generateAsync.set(true)
            verifyMigrations.set(true)
        }
    }
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
