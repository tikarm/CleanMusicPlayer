package tigran.applications.musicplayer.song_list_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tigran.applications.core.CurrentSongInfo
import tigran.applications.musicplayer.domain.use_cases.PlaySongUseCase
import tigran.applications.musicplayer.song_list_domain.use_cases.GetSongsUseCase
import tigran.applications.musicplayer.song_model.SongModel
import tigran.applications.musicplayer.song_ui_state.SongUiState
import javax.inject.Inject


@HiltViewModel
class SongListViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val playSongUseCase: PlaySongUseCase,
) : ViewModel() {

    private var songList: List<SongModel> = emptyList()

    private val _songListUiState = MutableStateFlow(SongListUiState())
    val songListUiState = _songListUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _songListUiState.value = SongListUiState(
                isLoading = true
            )
            songList = getSongsUseCase.invoke()
            _songListUiState.value = _songListUiState.value.copy(
                isLoading = false,
                songList = songList.map { it.toSongUiState() }
            )
        }
    }

    fun onSongClicked(id: String, currentPlayingSongInfo: CurrentSongInfo?) {
        val songToPlay = songList.first { it.id == id }
        playSongUseCase.invoke(songToPlay, currentPlayingSongInfo)
    }

    private fun SongModel.toSongUiState(): SongUiState {
        return SongUiState(
            id = id,
            title = title,
            artist = artist,
            album = album,
            albumArtUri = albumArtUri
        )
    }

    data class SongListUiState(
        val isLoading: Boolean = false,
        val songList: List<SongUiState> = emptyList()
    )
}