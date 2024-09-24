plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.tigran.applications.MusicPlayer.main_screen.presentation"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.mainScreenDomain))
    implementation(project(Modules.songModel))
    implementation(project(Modules.songUiState))
    implementation(project(Modules.domain))

    implementation(project(Modules.songListPresentation))
    implementation(project(Modules.currentSongPresentation))
}