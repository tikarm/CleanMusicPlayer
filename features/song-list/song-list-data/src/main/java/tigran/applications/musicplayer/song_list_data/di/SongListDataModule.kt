package tigran.applications.musicplayer.song_list_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tigran.applications.musicplayer.song_list_data.repository.SongListRepositoryImpl
import tigran.applications.musicplayer.song_list_domain.repository.SongListRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class SongListDataModule {
    @Binds
    abstract fun provideSongListRepository(repository: SongListRepositoryImpl): SongListRepository
}