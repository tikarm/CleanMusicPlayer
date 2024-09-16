plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    kotlin(Serialization.kotlinSerilizationPlugin) version Serialization.kotlinSerializationPluginVersion
}

android {
    namespace = "tigran.applications.musicplayer"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Coil.coilCompose)

    implementation(Google.material)

    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    kaptAndroidTest(DaggerHilt.hiltCompiler)

    implementation(Navigation.navigationCompose)
    implementation(Serialization.kotlinSerialization)

    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))

    implementation(project(Modules.songListPresentation))
    implementation(project(Modules.songListData))
    implementation(project(Modules.songListDomain))

    implementation(project(Modules.currentSongPresentation))
    implementation(project(Modules.currentSongData))
    implementation(project(Modules.currentSongDomain))

    implementation(project(Modules.dataLocal))
    implementation(project(Modules.dataDevice))
    implementation(project(Modules.domain))
    implementation(project(Modules.player))
    implementation(project(Modules.songModel))
    implementation(project(Modules.songUiState))

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}