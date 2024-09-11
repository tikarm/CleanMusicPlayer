package tigran.applications.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SongInteractor {
    private val _currentPlayingSongInfo = MutableStateFlow<Pair<String, Boolean>?>(null)
    val currentPlayingSongInfo = _currentPlayingSongInfo.asStateFlow()

    fun setSongIsPlaying(id: String, isPlaying: Boolean) {
        _currentPlayingSongInfo.value = Pair(id, isPlaying)
    }
}