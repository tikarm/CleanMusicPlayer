pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MusicPlayer"
include(":app")
include(":core")
include(":core-ui")
include(":features")
include(":data")
include(":data:local")
include(":data:device")

include(":song-model")

include(":features:song-list")
include(":features:song-list:song-list-presentation")
include(":features:song-list:song-list-domain")
include(":features:song-list:song-list-data")

include(":player")