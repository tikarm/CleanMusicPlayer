package tigran.applications.musicplayer.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun play(uri: Uri) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = "PLAY_SONG"
            putExtra("SONG_URI", uri.toString())
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun stop() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = "STOP_PLAYBACK"
        }
        context.stopService(intent)
    }

    fun pause() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = "PAUSE_PLAYBACK"
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun resume() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = "RESUME_PLAYBACK"
        }
        ContextCompat.startForegroundService(context, intent)
    }

//    fun isPlaying(): Boolean {
////        return mediaPlayer.isPlaying
//    }
}

