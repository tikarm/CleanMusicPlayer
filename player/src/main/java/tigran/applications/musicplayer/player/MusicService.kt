package tigran.applications.musicplayer.player

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.tigran.applications.MusicPlayer.player.R

class MusicService : Service() {

    companion object {
        const val SERVICE_NOTIFICATION_ID = 1
        const val CHANNEL_ID = "music_channel_id"
    }

    private var mediaPlayer = MediaPlayer()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        val songUri = intent?.getStringExtra("SONG_URI")?.let { Uri.parse(it) }

        when (action) {
            "PLAY_SONG" -> {
                songUri?.let { playSong(it) }
            }

            "STOP_PLAYBACK" -> {
                stopPlayback()
            }

            "PAUSE_PLAYBACK" -> {
                pausePlayback()
            }

            "RESUME_PLAYBACK" -> {
                resumePlayback()
            }
        }

        startForeground(SERVICE_NOTIFICATION_ID, createNotification())

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopPlayback()
        super.onDestroy()
    }

    private fun playSong(uri: Uri) {
        stopPlayback() // Stop any ongoing playback
        mediaPlayer.apply {
            setDataSource(applicationContext, uri)
            prepare()
            start()
        }
    }

    private fun stopPlayback() {
        mediaPlayer.apply {
            stop()
            release()
        }
    }

    private fun pausePlayback() {
        mediaPlayer.apply {
            if (isPlaying) {
                pause()
            }
        }
    }

    private fun resumePlayback() {
        mediaPlayer.apply {
            if (!isPlaying) {
                start()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Playing")
            .setContentText("Երգում ա")
            .setSmallIcon(R.drawable.ic_music_note)
            .build()
    }
}

