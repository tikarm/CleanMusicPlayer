package tigran.applications.musicplayer.player_interaction

import tigran.applications.musicplayer.song_model.SongModel

interface AudioPlayer {
    fun play(song: SongModel)

    fun pause()

    fun stop()

    fun resume()
}