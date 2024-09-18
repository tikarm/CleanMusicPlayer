package tigran.applications.musicplayer.current_song_data.repository

import tigran.applications.musicplayer.current_song_domain.repository.SongRepository
import tigran.applications.musicplayer.data.local.datasource.LocalDataSource
import tigran.applications.musicplayer.data.local.entities.SongEntity
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : SongRepository {

    override suspend fun getSong(id: String): SongModel {
        return localDataSource.getSongById(id)
    }
}