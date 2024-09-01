package tigran.applications.musicplayer.data.device

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import tigran.applications.musicplayer.data.local.entities.SongEntity
import javax.inject.Inject

class DeviceStorageDataSource @Inject constructor() {
    fun getAllSongs(contentResolver: ContentResolver): List<SongEntity> {
        val songs = mutableListOf<SongEntity>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        contentResolver.query(uri, projection, selection, null, sortOrder)?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val albumIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val album = cursor.getString(albumColumn)
                val data = cursor.getString(dataColumn)
                val albumId = cursor.getString(albumIdColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id.toLong()
                ).toString()

                val albumArtUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    albumId.toLong()
                ).toString()

                songs.add(
                    SongEntity(
                        id = id,
                        title = title,
                        artist = artist,
                        album = album,
                        albumArtUri = albumArtUri,
                        filePath = data,
                        contentUri = contentUri
                    )
                )
            }
        }
        return songs
    }
}