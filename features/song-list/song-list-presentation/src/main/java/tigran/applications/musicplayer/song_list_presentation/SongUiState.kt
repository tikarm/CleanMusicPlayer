package tigran.applications.musicplayer.song_list_presentation

data class SongUiState(
    val id: String,
    val title: String = "",
    val artist: String? = "",
    val album: String? = "",
    val albumArtUri: String? = null
)