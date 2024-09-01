package tigran.applications.musicplayer.song_model

data class SongModel(
    val id: String,
    val title: String,
    val artist: String?,
    val album: String?,
    val albumArtUri: String?,
    val contentUri: String?,
)