package tigran.applications.musicplayer.song_list_domain.use_cases

import androidx.core.net.toUri
import tigran.applications.musicplayer.player.AudioPlayer
import javax.inject.Inject

class PlaySongUseCase @Inject constructor(private val audioPlayer: AudioPlayer) {

    operator fun invoke(songUri: String) {
        audioPlayer.play(songUri.toUri())
    }
}