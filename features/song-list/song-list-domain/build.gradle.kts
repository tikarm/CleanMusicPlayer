plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.tigran.applications.MusicPlayer.song_list.domain"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.playerInteraction))
    implementation(project(Modules.songModel))
}