package tigran.applications.musicplayer.song_ui_state

data class SongUiState(
    val id: String,
    val title: String = "",
    val artist: String? = "",
    val album: String? = "",
    val albumArtUri: String? = null,
    val position: Int = -1,
    var isPlaying: Boolean? = false
)