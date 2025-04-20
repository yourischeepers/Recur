import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlin.serialization)
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

android {
    namespace = "me.partypronl.recur"
    compileSdk = 35

    defaultConfig {
        applicationId = "me.partypronl.recur"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("config") {
            storeFile = file(gradleLocalProperties(rootDir, providers).getProperty("RELEASE_STORE_FILE"))
            storePassword = gradleLocalProperties(rootDir, providers).getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = gradleLocalProperties(rootDir, providers).getProperty("RELEASE_KEY_ALIAS")
            keyPassword = gradleLocalProperties(rootDir, providers).getProperty("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            isDefault = true
            isDebuggable = true
            isMinifyEnabled = false
        }

        create("acceptance") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("config")
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Kotlin
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.reflect)
    implementation(libs.atomicfu)

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.core)
    implementation(libs.bundles.koin.android)
    ksp(libs.koin.ksp)

    // SQL Delight
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
}
