package tigran.applications.musicplayer.song_list_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
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

    val songListUiState: StateFlow<SongListUiState> = flow {
        emit(SongListUiState(isLoading = true))
        songList = withContext(Dispatchers.IO) {
            getSongsUseCase.invoke()
        }
        emit(
            SongListUiState(
                isLoading = false,
                songList = songList.map { it.toSongUiState() }
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SongListUiState()
    )

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