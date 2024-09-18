package tigran.applications.musicplayer.current_song_domain.use_cases

import tigran.applications.musicplayer.player_interaction.AudioPlayer
import javax.inject.Inject

class PlayNextSongUseCase @Inject constructor(private val audioPlayer: AudioPlayer) {

    operator fun invoke() {
        audioPlayer.playNextSong()
    }
}