package tigran.applications.musicplayer.song_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongModel(
    val id: String,
    val title: String,
    val artist: String?,
    val album: String?,
    val albumArtUri: String?,
    val contentUri: String?,
) : Parcelable