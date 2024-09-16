package tigran.applications.musicplayer.song_list_domain.repository

import tigran.applications.musicplayer.song_model.SongModel

interface SongListRepository {
    suspend fun getAllSongs(): List<SongModel>

    suspend fun refreshSongs()
}