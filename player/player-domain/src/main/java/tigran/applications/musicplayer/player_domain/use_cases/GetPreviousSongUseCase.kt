package tigran.applications.musicplayer.player_domain.use_cases

import tigran.applications.musicplayer.player_domain.repository.SongRepository
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class GetPreviousSongUseCase @Inject constructor(private val repository: SongRepository) {

    suspend operator fun invoke(currentSongPosition: Int): SongModel {
        return repository.getPreviousSong(currentSongPosition)
    }
}