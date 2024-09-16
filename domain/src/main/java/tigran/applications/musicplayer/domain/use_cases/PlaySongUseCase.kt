package tigran.applications.musicplayer.domain.use_cases

import tigran.applications.musicplayer.player.AudioPlayer
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class PlaySongUseCase @Inject constructor(private val audioPlayer: AudioPlayer) {

    operator fun invoke(
        selectedSongModel: SongModel,
        currentPlayingSongInfo: Pair<String, Boolean>?
    ) {
        if (currentPlayingSongInfo?.first == selectedSongModel.id) {
            if (currentPlayingSongInfo.second) {
                audioPlayer.pause()
            } else {
                audioPlayer.resume()
            }
        } else {
            audioPlayer.play(selectedSongModel)
        }
    }
}