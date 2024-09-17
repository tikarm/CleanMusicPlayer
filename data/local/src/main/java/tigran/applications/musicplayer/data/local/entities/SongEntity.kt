package tigran.applications.musicplayer.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "artist")
    val artist: String?,

    @ColumnInfo(name = "album")
    val album: String?,

    @ColumnInfo(name = "albumArtUri")
    val albumArtUri: String?,

    @ColumnInfo(name = "filePath")
    val filePath: String,

    @ColumnInfo(name = "contentUri")
    val contentUri: String,

    @ColumnInfo(name = "position")
    val position: Int,
)