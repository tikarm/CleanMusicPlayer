package tigran.applications.musicplayer.song_list_domain.use_cases

import androidx.core.net.toUri
import tigran.applications.musicplayer.player.AudioPlayer
import javax.inject.Inject

class PlaySongUseCase @Inject constructor(private val audioPlayer: AudioPlayer) {

    fun startSong(songUri: String) {
        audioPlayer.play(songUri.toUri())
    }

    fun pauseSong() {
        audioPlayer.pause()
    }

    fun resumeSong() {
        audioPlayer.resume()
    }
}