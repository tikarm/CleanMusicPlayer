package tigran.applications.musicplayer.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import dagger.hilt.android.qualifiers.ApplicationContext
import tigran.applications.musicplayer.player.MusicService.Companion.PAUSE_SONG_ACTION
import tigran.applications.musicplayer.player.MusicService.Companion.PLAY_SONG_ACTION
import tigran.applications.musicplayer.player.MusicService.Companion.RESUME_SONG_ACTION
import tigran.applications.musicplayer.player.MusicService.Companion.SONG_EXTRA
import tigran.applications.musicplayer.player.MusicService.Companion.SONG_URI_EXTRA
import tigran.applications.musicplayer.player.MusicService.Companion.STOP_SONG_ACTION
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class AudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun play(song: SongModel) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = PLAY_SONG_ACTION

            val bundle = Bundle()
            bundle.putParcelable(SONG_EXTRA, song)

            putExtra(SONG_URI_EXTRA, bundle)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun pause() {
        sendBroadcast(PAUSE_SONG_ACTION)
    }

    fun stop() {
        sendBroadcast(STOP_SONG_ACTION)
    }

    fun resume() {
        sendBroadcast(RESUME_SONG_ACTION)
    }

    private fun sendBroadcast(action: String) {
        val intent = Intent(action).apply {
            `package` = context.packageName
        }
        context.sendBroadcast(intent)
    }
}


