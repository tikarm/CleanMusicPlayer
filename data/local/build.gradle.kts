plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.tigran.applications.MusicPlayer.data.local"
}

dependencies {
    implementation(project(Modules.core))

    implementation(Preferences.preferenceKtx)
    implementation(SecurityCrypto.securityCrypto)
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
    "kapt"(Room.roomCompiler)
    implementation(Google.gson)
}