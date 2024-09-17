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

include(":features:current-song")
include(":features:current-song:current-song-presentation")
include(":features:current-song:current-song-domain")
include(":features:current-song:current-song-data")
include(":song-ui-state")
include(":domain")
include(":player")
include(":player:player-presentation")
include(":player:player-data")
include(":player:player-domain")
include(":player:player-interaction")
