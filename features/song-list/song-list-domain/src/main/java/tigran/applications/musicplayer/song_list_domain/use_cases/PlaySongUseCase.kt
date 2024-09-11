package tigran.applications.musicplayer.song_list_domain.use_cases

import androidx.core.net.toUri
import tigran.applications.musicplayer.player.AudioPlayer
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class PlaySongUseCase @Inject constructor(private val audioPlayer: AudioPlayer) {

    fun startSong(songModel: SongModel) {
        audioPlayer.play(songModel)
    }

    fun pauseSong() {
        audioPlayer.pause()
    }

    fun resumeSong() {
        audioPlayer.resume()
    }
}