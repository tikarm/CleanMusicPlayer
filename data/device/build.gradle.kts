plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.tigran.applications.MusicPlayer.data.device"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.dataLocal))
}