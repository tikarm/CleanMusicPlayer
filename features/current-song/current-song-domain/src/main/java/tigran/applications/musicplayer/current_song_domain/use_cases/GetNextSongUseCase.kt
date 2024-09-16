package tigran.applications.musicplayer.current_song_domain.use_cases

import tigran.applications.musicplayer.current_song_domain.repository.SongRepository
import tigran.applications.musicplayer.player.AudioPlayer
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class GetNextSongUseCase @Inject constructor(private val repository: SongRepository) {

    suspend operator fun invoke(currentSongPosition: Int): SongModel {
        return repository.getNextSong(currentSongPosition)
    }
}