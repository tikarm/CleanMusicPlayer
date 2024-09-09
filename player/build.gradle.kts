plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin(Serialization.kotlinSerilizationPlugin) version Serialization.kotlinSerializationPluginVersion
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.tigran.applications.MusicPlayer.player"
}

dependencies {
    implementation(project(Modules.songModel))
}