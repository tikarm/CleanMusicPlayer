package tigran.applications.musicplayer.song_list_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tigran.applications.musicplayer.song_list_domain.use_cases.GetSongsUseCase
import tigran.applications.musicplayer.song_list_domain.use_cases.PlaySongUseCase
import tigran.applications.musicplayer.song_model.SongModel
import javax.inject.Inject


@HiltViewModel
class SongListViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val playSongUseCase: PlaySongUseCase,
) : ViewModel() {

    private var songList: List<SongModel> = emptyList()

    private val _currentSongUiState = MutableStateFlow<SongUiState?>(null)
    val currentSongUiState = _currentSongUiState.asStateFlow()

    private val _songListUiState = MutableStateFlow(listOf<SongUiState>())
    val songListUiState = _songListUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            songList = getSongsUseCase.invoke()
            _songListUiState.value = songList.map { it.toSongUiState() }
        }
    }

    fun onSongClicked(songUiState: SongUiState) {
        if(_currentSongUiState.value?.id == songUiState.id) {
            if(_currentSongUiState.value?.isPlaying == true) {
                pauseSong()
            } else {
                resumeSong()
            }
            _currentSongUiState.value = _currentSongUiState.value?.copy(
                isPlaying = !_currentSongUiState.value?.isPlaying!!
            )
        } else {
            playSong(songUiState.id)
            _currentSongUiState.value = songUiState.copy(
                isPlaying = true
            )
        }
    }

    private fun playSong(songId: String) {
        val songToPlay = songList.first { it.id == songId }
        songToPlay.contentUri?.let { playSongUseCase.startSong(it) }
    }

    private fun pauseSong() {
        playSongUseCase.pauseSong()
    }

    private fun resumeSong() {
        playSongUseCase.resumeSong()
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
}