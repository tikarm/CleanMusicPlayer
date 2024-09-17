package tigran.applications.musicplayer.player_presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tigran.applications.musicplayer.player_interaction.AudioPlayer
import tigran.applications.musicplayer.player_presentation.AudioPlayerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioPlayerModule {

    @Provides
    @Singleton
    fun provideAudioPlayer(@ApplicationContext context: Context): AudioPlayer {
        return AudioPlayerImpl(context)
    }
}
