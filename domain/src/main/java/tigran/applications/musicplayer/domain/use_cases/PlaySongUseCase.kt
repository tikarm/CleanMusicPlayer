package tigran.applications.musicplayer.domain.use_cases

import tigran.applications.core.CurrentSongInfo
import tigran.applications.musicplayer.player_interaction.AudioPlayer
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class PlaySongUseCase @Inject constructor(private val audioPlayer: AudioPlayer) {

    operator fun invoke(
        selectedSongModel: SongModel,
        currentPlayingSongInfo: CurrentSongInfo?
    ) {
        if (currentPlayingSongInfo?.id == selectedSongModel.id) {
            if (currentPlayingSongInfo.isPlaying) {
                audioPlayer.pause()
            } else {
                audioPlayer.resume()
            }
        } else {
            audioPlayer.play(selectedSongModel)
        }
    }
}