package tigran.applications.musicplayer.current_song_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tigran.applications.musicplayer.current_song_domain.use_cases.GetSongUseCase
import tigran.applications.musicplayer.current_song_domain.use_cases.PlayNextSongUseCase
import tigran.applications.musicplayer.current_song_domain.use_cases.PlayPreviousSongUseCase
import tigran.applications.musicplayer.domain.use_cases.PlaySongUseCase
import tigran.applications.musicplayer.song_model.SongModel
import tigran.applications.musicplayer.song_ui_state.SongUiState
import javax.inject.Inject

@HiltViewModel
class CurrentPlayingSongViewModel @Inject constructor(
    private val getSongUseCase: GetSongUseCase,
    private val playSongUseCase: PlaySongUseCase,
    private val playNextSongUseCase: PlayNextSongUseCase,
    private val playPreviousSongUseCase: PlayPreviousSongUseCase,
) : ViewModel() {

    private val _songUiState = MutableStateFlow<SongUiState?>(null)
    val songUiState = _songUiState.asStateFlow()

    private var currentSong: SongModel? = null

    fun getSong(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currentSong = getSongUseCase.invoke(id)
            _songUiState.value = currentSong?.toSongUiState()?.apply {
                isPlaying = true
            }
        }
    }

    fun playNextSong() {
        playNextSongUseCase.invoke()
    }

    fun playPreviousSong() {
        playPreviousSongUseCase.invoke()
    }

    fun onPlayPauseClicked(
        currentPlayingSongInfo: Pair<String, Boolean>?
    ) {
        if (currentSong != null) {
            playSongUseCase.invoke(currentSong!!, currentPlayingSongInfo)
        }
    }

    fun setSongUiStateIsPlaying(isPlaying: Boolean) {
        _songUiState.value = _songUiState.value?.copy(
            isPlaying = isPlaying
        )
    }

    private fun SongModel.toSongUiState(): SongUiState {
        return SongUiState(
            id = id,
            title = title,
            artist = artist,
            album = album,
            albumArtUri = albumArtUri,
            position = position
        )
    }
}