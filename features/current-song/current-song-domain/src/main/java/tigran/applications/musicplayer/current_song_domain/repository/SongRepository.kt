package tigran.applications.musicplayer.current_song_domain.repository

import tigran.applications.musicplayer.song_model.SongModel

interface SongRepository {
    suspend fun getSong(id: String): SongModel
}