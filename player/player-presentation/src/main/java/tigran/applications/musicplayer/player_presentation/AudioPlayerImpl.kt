package tigran.applications.musicplayer.player_presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import tigran.applications.musicplayer.player_interaction.AudioPlayer
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.NEXT_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.PAUSE_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.PLAY_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.PREVIOUS_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.RESUME_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.SONG_EXTRA
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.SONG_URI_EXTRA
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.STOP_SONG_ACTION
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AudioPlayer {
    override fun play(song: SongModel) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = PLAY_SONG_ACTION

            val bundle = Bundle()
            bundle.putParcelable(SONG_EXTRA, song)

            putExtra(SONG_URI_EXTRA, bundle)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun pause() {
        sendBroadcast(PAUSE_SONG_ACTION)
    }

    override fun stop() {
        sendBroadcast(STOP_SONG_ACTION)
    }

    override fun resume() {
        sendBroadcast(RESUME_SONG_ACTION)
    }

    override fun playNextSong() {
        sendBroadcast(NEXT_SONG_ACTION)
    }

    override fun playPreviousSong() {
        sendBroadcast(PREVIOUS_SONG_ACTION)
    }

    private fun sendBroadcast(action: String) {
        val intent = Intent(action).apply {
            `package` = context.packageName
        }
        context.sendBroadcast(intent)
    }
}


