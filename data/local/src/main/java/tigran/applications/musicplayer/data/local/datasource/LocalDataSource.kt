package tigran.applications.musicplayer.data.local.datasource

import tigran.applications.musicplayer.data.local.dao.SongDao
import tigran.applications.musicplayer.data.local.entities.SongEntity
import tigran.applications.musicplayer.data.local.exceptions.DatabaseInsertionException
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val songDao: SongDao
) {
    fun getAllSongs() = songDao.getAll().map { it.toSongModel() }

    fun getSongById(id: String) = songDao.getSongById(id).toSongModel()

    @Throws(DatabaseInsertionException::class)
    fun insertSong(item: SongEntity): Long {
        val id = songDao.insert(item)
        if (id == -1L) {
            throw DatabaseInsertionException()
        }
        return id
    }

    fun insertAllSongs(songs: List<SongEntity>) {
        songDao.insertSongs(songs)
    }

    fun getNextSong(currentSongPosition: Int) = songDao.getSongByPosition(currentSongPosition + 1)

    fun getPreviousSong(currentSongPosition: Int) = songDao.getSongByPosition(currentSongPosition - 1)

    fun removeSong(id: String) = songDao.remove(id)

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