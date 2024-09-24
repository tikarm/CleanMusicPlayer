package tigran.applications.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SongInteractor {
    private val _currentPlayingSongInfo = MutableSharedFlow<Pair<String, Boolean>?>(replay = 1)
    val currentPlayingSongInfo = _currentPlayingSongInfo.asSharedFlow()

    suspend fun setCurrentSongInfo(id: String, isPlaying: Boolean) {
        _currentPlayingSongInfo.emit(Pair(id, isPlaying))
    }
}