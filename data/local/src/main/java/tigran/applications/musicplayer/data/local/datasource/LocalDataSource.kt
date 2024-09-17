package tigran.applications.musicplayer.data.local.datasource

import tigran.applications.musicplayer.data.local.dao.SongDao
import tigran.applications.musicplayer.data.local.entities.SongEntity
import tigran.applications.musicplayer.data.local.exceptions.DatabaseInsertionException
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val songDao: SongDao
) {
    fun getAllSongs() = songDao.getAll()

    fun getSongById(id: String) = songDao.getSongById(id)

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
}