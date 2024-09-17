package tigran.applications.musicplayer.player_domain.repository

import tigran.applications.musicplayer.song_model.SongModel

interface SongRepository {
    suspend fun getNextSong(currentSongPosition: Int): SongModel

    suspend fun getPreviousSong(currentSongPosition: Int): SongModel
}