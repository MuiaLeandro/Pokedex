plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "1.6.21"
    id("app.cash.sqldelight") version "2.0.2"
}

repositories {
    google()
    mavenCentral()
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    sourceSets {
        val ktor_version = "2.0.0"
        val napier_version = "2.6.1"
        val sqldelight_version = "2.0.2"

        commonMain.dependencies {
            //Ktor
            implementation("io.ktor:ktor-client-core:$ktor_version")
            implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
            implementation("io.ktor:ktor-client-logging:$ktor_version")

            // Librería de logging multiplataforma
            implementation("io.github.aakira:napier:$napier_version")

            //SQLDelight
            implementation("app.cash.sqldelight:runtime:$sqldelight_version")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            //Ktor
            implementation("io.ktor:ktor-client-okhttp:$ktor_version")
            //SQLDelight
            implementation("app.cash.sqldelight:android-driver:$sqldelight_version")
        }

        iosMain.dependencies {
            //Ktor
            implementation("io.ktor:ktor-client-ios:$ktor_version")
            //SQLDelight
            implementation("app.cash.sqldelight:native-driver:$sqldelight_version")
        }
    }
}

sqldelight {
    databases {
        create("PokedexDB") {
            packageName.set("com.example.pokedexkmm_muia")
        }
    }
}

android {
    namespace = "com.example.pokedexkmm_muia"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}