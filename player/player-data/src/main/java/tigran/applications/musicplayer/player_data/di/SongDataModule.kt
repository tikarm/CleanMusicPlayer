package tigran.applications.musicplayer.player_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import tigran.applications.musicplayer.player_data.repostiory.SongRepositoryImpl
import tigran.applications.musicplayer.player_domain.repository.SongRepository

@Module
@InstallIn(ServiceComponent::class)
abstract class SongDataModule {
    @Binds
    abstract fun provideSongRepository(repository: SongRepositoryImpl): SongRepository
}