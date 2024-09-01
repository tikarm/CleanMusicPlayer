package tigran.applications.musicplayer.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tigran.applications.musicplayer.data.local.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataLocalModule {
    private const val DATABASE_NAME = "music_player"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context) = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    fun provideSongDao(db: AppDatabase) = db.songDao()
}