package tigran.applications.musicplayer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tigran.applications.musicplayer.data.local.dao.SongDao
import tigran.applications.musicplayer.data.local.entities.SongEntity

@Database(entities = [SongEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}