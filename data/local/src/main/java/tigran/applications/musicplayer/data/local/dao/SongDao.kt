package tigran.applications.musicplayer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import tigran.applications.musicplayer.data.local.entities.SongEntity

@Dao
interface SongDao {
    @Query("SELECT * FROM songs")
    fun getAll(): List<SongEntity>

    @Query("SELECT * FROM songs WHERE id =:id")
    fun getSongById(id: Long): SongEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: SongEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(songs: List<SongEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(song: SongEntity)

    @Query("DELETE FROM songs WHERE id =:id")
    fun remove(id: Long)
}