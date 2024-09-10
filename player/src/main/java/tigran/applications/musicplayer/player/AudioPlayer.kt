package tigran.applications.musicplayer.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import tigran.applications.musicplayer.player.MusicService.Companion.PAUSE_SONG_ACTION
import tigran.applications.musicplayer.player.MusicService.Companion.PLAY_SONG_ACTION
import tigran.applications.musicplayer.player.MusicService.Companion.RESUME_SONG_ACTION
import tigran.applications.musicplayer.player.MusicService.Companion.SONG_URI_EXTRA
import tigran.applications.musicplayer.player.MusicService.Companion.STOP_SONG_ACTION
import javax.inject.Inject

class AudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun play(uri: Uri) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = PLAY_SONG_ACTION
            putExtra(SONG_URI_EXTRA, uri.toString())
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun stop() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = STOP_SONG_ACTION
        }
        context.stopService(intent)
    }

    fun pause() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = PAUSE_SONG_ACTION
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun resume() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = RESUME_SONG_ACTION
        }
        ContextCompat.startForegroundService(context, intent)
    }
}

