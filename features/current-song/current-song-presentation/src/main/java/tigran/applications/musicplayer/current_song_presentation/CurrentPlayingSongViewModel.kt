package tigran.applications.musicplayer.current_song_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import tigran.applications.core.CurrentSongInfo
import tigran.applications.core.SongInteractor
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

    private var currentSong: SongModel? = null

    val songUiState: StateFlow<SongUiState?> = SongInteractor.currentPlayingSongInfo
        .filterNotNull()
        .mapLatest { songInfo ->
            val song = withContext(Dispatchers.IO) {
                getSongUseCase.invoke(songInfo.id)
            }
            currentSong = song
            song.toSongUiState().copy(isPlaying = songInfo.isPlaying)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun playNextSong() {
        playNextSongUseCase.invoke()
    }

    fun playPreviousSong() {
        playPreviousSongUseCase.invoke()
    }

    fun onPlayPauseClicked() {
        if (currentSong != null) {
            playSongUseCase.invoke(
                selectedSongModel = currentSong!!,
                currentPlayingSongInfo = CurrentSongInfo(
                    id = songUiState.value!!.id,
                    isPlaying = songUiState.value?.isPlaying == true
                )
            )
        }
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