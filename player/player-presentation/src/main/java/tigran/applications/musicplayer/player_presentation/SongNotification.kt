package tigran.applications.musicplayer.player_presentation

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.tigran.applications.MusicPlayer.player.presentation.R
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.CHANNEL_ID
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.NEXT_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.NOTIFICATION_ID
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.PAUSE_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.PREVIOUS_SONG_ACTION
import tigran.applications.musicplayer.player_presentation.MusicService.Companion.RESUME_SONG_ACTION
import tigran.applications.musicplayer.song_model.SongModel

class SongNotification(
    private val context: Context,
) {
    private var remoteView: RemoteViews? = null

    private var notificationManager: NotificationManager? = null
    private var notification: Notification? = null

    fun createNotification(song: SongModel): Notification {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        remoteView = RemoteViews(context.packageName, R.layout.layout_notification).apply {
            setTextViewText(R.id.song_title, song.title)
            setTextViewText(R.id.artist_name, song.artist)
            setImageViewResource(
                R.id.play_pause_button,
                R.drawable.ic_pause
            )

            setOnClickPendingIntent(
                R.id.play_pause_button,
                getPendingIntent(
                    PAUSE_SONG_ACTION
                )
            )
            setOnClickPendingIntent(
                R.id.next_button,
                getPendingIntent(
                    NEXT_SONG_ACTION
                )
            )

            setOnClickPendingIntent(
                R.id.prev_button,
                getPendingIntent(
                    PREVIOUS_SONG_ACTION
                )
            )
        }

        notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_music_note)
            .setContent(remoteView)
            .setCustomBigContentView(remoteView)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .build()

        return notification!!
    }

    fun updateNotification(song: SongModel, isPlaying: Boolean) {
        remoteView?.apply {
            setTextViewText(R.id.song_title, song.title)
            setTextViewText(R.id.artist_name, song.artist)
            setImageViewResource(
                R.id.play_pause_button,
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
            )

            setOnClickPendingIntent(
                R.id.play_pause_button,
                getPendingIntent(
                    if (isPlaying)
                        PAUSE_SONG_ACTION
                    else RESUME_SONG_ACTION
                )
            )
        }

        if (notificationManager != null && notification != null) {
            notificationManager!!.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(
            context,
            MusicService::class.java
        ).apply {
            this.action = action
        }
        return PendingIntent.getService(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}