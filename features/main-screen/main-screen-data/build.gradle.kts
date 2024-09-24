plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.tigran.applications.MusicPlayer.main_screen.data"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.mainScreenDomain))
    implementation(project(Modules.dataLocal))
    implementation(project(Modules.dataDevice))
    implementation(project(Modules.songModel))
}