package tigran.applications.musicplayer.current_song_domain.use_cases

import tigran.applications.musicplayer.current_song_domain.repository.SongRepository
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject


class GetSongUseCase @Inject constructor(private val repository: SongRepository) {

    suspend operator fun invoke(id: String): SongModel {
        return repository.getSong(id)
    }
}