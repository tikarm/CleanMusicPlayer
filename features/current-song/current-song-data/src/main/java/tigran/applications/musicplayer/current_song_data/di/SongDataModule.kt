package tigran.applications.musicplayer.current_song_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tigran.applications.musicplayer.current_song_data.repository.SongRepositoryImpl
import tigran.applications.musicplayer.current_song_domain.repository.SongRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class SongDataModule {
    @Binds
    abstract fun provideSongRepository(repository: SongRepositoryImpl): SongRepository
}