package tigran.applications.musicplayer.song_list_domain.use_cases

import tigran.applications.musicplayer.song_list_domain.repository.SongListRepository
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(private val repository: SongListRepository) {

    suspend operator fun invoke(): List<SongModel> {
        repository.refreshSongs()

        return repository.getAllSongs()
    }
}
