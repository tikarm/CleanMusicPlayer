package tigran.applications.musicplayer.song_list_data.repository

import android.content.ContentResolver
import tigran.applications.musicplayer.data.device.DeviceStorageDataSource
import tigran.applications.musicplayer.data.local.datasource.LocalDataSource
import tigran.applications.musicplayer.data.local.entities.SongEntity
import tigran.applications.musicplayer.song_model.SongModel
import tigran.applications.musicplayer.song_list_domain.repository.SongListRepository
import javax.inject.Inject

class SongListRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val localDataSource: LocalDataSource,
    private val deviceStorageDataSource: DeviceStorageDataSource
) : SongListRepository {

    override suspend fun getAllSongs(): List<SongModel> {
        return localDataSource.getAllSongs().map { it.toSongModel() }
    }

    override suspend fun refreshSongs() {
        val songs = deviceStorageDataSource.getAllSongs(contentResolver)
        localDataSource.insertAllSongs(songs)
    }

    private fun SongEntity.toSongModel(): SongModel {
        return SongModel(
            id = id,
            title = title,
            artist = artist,
            album = album,
            albumArtUri = albumArtUri,
            contentUri = contentUri
        )
    }
}
