package tigran.applications.core.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object SongListScreen : Screen()
}