package tigran.applications.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SongInteractor {
    private val _currentPlayingSongInfo = MutableSharedFlow<CurrentSongInfo?>(replay = 1)
    val currentPlayingSongInfo = _currentPlayingSongInfo.asSharedFlow()

    suspend fun setCurrentSongInfo(id: String, isPlaying: Boolean) {
        _currentPlayingSongInfo.emit(CurrentSongInfo(id, isPlaying))
    }
}

class CurrentSongInfo(
    val id: String,
    val isPlaying: Boolean
)