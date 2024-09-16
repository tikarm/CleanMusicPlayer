package tigran.applications.musicplayer.player

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tigran.applications.core.SongInteractor
import tigran.applications.musicplayer.song_model.SongModel

class MusicService : Service() {

    companion object {
        const val SERVICE_NOTIFICATION_ID = 1
        const val CHANNEL_ID = "music_channel_id"

        const val SONG_EXTRA = "SONG_EXTRA"
        const val SONG_URI_EXTRA = "SONG_URI_EXTRA"

        const val PLAY_SONG_ACTION = "PLAY_SONG_ACTION"
        const val STOP_SONG_ACTION = "STOP_SONG_ACTION"
        const val PAUSE_SONG_ACTION = "PAUSE_SONG_ACTION"
        const val RESUME_SONG_ACTION = "RESUME_SONG_ACTION"
    }

    private var mediaPlayer = MediaPlayer()

    private var currentSong: SongModel? = null
    val songNotification = SongNotification(this)

    private val musicActionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                PAUSE_SONG_ACTION -> pausePlayback()
                RESUME_SONG_ACTION -> resumePlayback()
                STOP_SONG_ACTION -> stopPlayback()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(musicActionReceiver,
            IntentFilter().apply {
                addAction(PLAY_SONG_ACTION)
                addAction(PAUSE_SONG_ACTION)
                addAction(RESUME_SONG_ACTION)
                addAction(STOP_SONG_ACTION)
            }
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action

        when (action) {
            PLAY_SONG_ACTION -> {
                val receivedBundle = intent.getBundleExtra(SONG_URI_EXTRA)
                currentSong = receivedBundle?.getParcelable(SONG_EXTRA)!!
                if (currentSong != null) {
                    playSong(Uri.parse(currentSong!!.contentUri))
                    startForeground(
                        SERVICE_NOTIFICATION_ID,
                        songNotification.createNotification(currentSong!!)
                    )
                }
            }

            STOP_SONG_ACTION -> {
                stopPlayback()
            }

            PAUSE_SONG_ACTION -> {
                pausePlayback()
            }

            RESUME_SONG_ACTION -> {
                resumePlayback()
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopPlayback()
        unregisterReceiver(musicActionReceiver)
        super.onDestroy()
    }

    private fun playSong(uri: Uri) {
        stopPlayback()
        mediaPlayer.apply {
            setDataSource(applicationContext, uri)
            prepare()
            start()
        }
        setCurrentPlayingSongState(true)
    }

    private fun stopPlayback() {
        mediaPlayer.apply {
            stop()
            reset()
        }
        setCurrentPlayingSongState(false)
    }

    private fun pausePlayback() {
        mediaPlayer.apply {
            if (isPlaying) {
                pause()
            }
        }
        setCurrentPlayingSongState(false)
    }

    private fun resumePlayback() {
        mediaPlayer.apply {
            if (!isPlaying) {
                start()
            }
        }
        setCurrentPlayingSongState(true)
    }

    private fun setCurrentPlayingSongState(isPlaying: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            SongInteractor.setSongIsPlaying(currentSong?.id!!, isPlaying)
            songNotification.updateNotification(currentSong!!, isPlaying)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

