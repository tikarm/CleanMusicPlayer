plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.tigran.applications.MusicPlayer.player.data"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.playerDomain))
    implementation(project(Modules.dataLocal))
    implementation(project(Modules.songModel))
}