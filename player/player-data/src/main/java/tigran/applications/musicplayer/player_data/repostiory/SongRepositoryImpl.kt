package tigran.applications.musicplayer.player_data.repostiory

import tigran.applications.musicplayer.data.local.datasource.LocalDataSource
import tigran.applications.musicplayer.data.local.entities.SongEntity
import tigran.applications.musicplayer.player_domain.exceptions.CannotGetSongException
import tigran.applications.musicplayer.player_domain.repository.SongRepository
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject


class SongRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : SongRepository {

    @Throws(CannotGetSongException::class)
    override suspend fun getNextSong(currentSongPosition: Int): SongModel {
        val song = localDataSource.getNextSong(currentSongPosition)
        if (song == null) {
            throw CannotGetSongException()
        } else {
            return song.toSongModel()
        }
    }

    @Throws(CannotGetSongException::class)
    override suspend fun getPreviousSong(currentSongPosition: Int): SongModel {
        val song = localDataSource.getPreviousSong(currentSongPosition)
        if (song == null) {
            throw CannotGetSongException()
        } else {
            return song.toSongModel()
        }
    }

    private fun SongEntity.toSongModel(): SongModel {
        return SongModel(
            id = id,
            title = title,
            artist = artist,
            album = album,
            albumArtUri = albumArtUri,
            contentUri = contentUri,
            position = position
        )
    }
}