package tigran.applications.musicplayer.song_list_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tigran.applications.musicplayer.song_model.SongModel
import tigran.applications.musicplayer.song_list_domain.use_cases.GetSongsUseCase
import tigran.applications.musicplayer.song_list_domain.use_cases.PlaySongUseCase
import javax.inject.Inject


@HiltViewModel
class SongListViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val playSongUseCase: PlaySongUseCase,
) : ViewModel() {

    private var songList: List<tigran.applications.musicplayer.song_model.SongModel> = emptyList()

    private val _songListUiState = MutableStateFlow(listOf<SongUiState>())
    val songListUiState = _songListUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            songList = getSongsUseCase.invoke()
            _songListUiState.value = songList.map { it.toSongUiState() }
        }
    }

    fun playSong(songId: String) {
        val songToPlay = songList.first { it.id == songId }
        songToPlay.contentUri?.let { playSongUseCase.invoke(it) }
    }

    private fun tigran.applications.musicplayer.song_model.SongModel.toSongUiState(): SongUiState {
        return SongUiState(
            id = id,
            title = title,
            artist = artist,
            album = album,
            albumArtUri = albumArtUri
        )
    }
}