package tigran.applications.musicplayer.song_list_presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tigran.applications.MusicPlayer.song_list.presentation.R
import tigran.applications.core.SongInteractor
import tigran.applications.core.util.UiEvent
import tigran.applications.musicplayer.core_ui.theme.defaultTextColor


@Composable
fun SongListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    songListViewModel: SongListViewModel = hiltViewModel(),
) {
    val songListUiState by songListViewModel.songListUiState.collectAsStateWithLifecycle()
    val currentPlayingSong by SongInteractor.currentPlayingSongInfo.collectAsStateWithLifecycle()

    LazyColumn {
        items(songListUiState.size) { index: Int ->
            var songUiState = songListUiState[index]

            songUiState = if (songUiState.id == currentPlayingSong?.first) {
                songUiState.copy(
                    isPlaying = currentPlayingSong?.second ?: false
                )
            } else {
                songUiState.copy(
                    isPlaying = null
                )
            }
            SongItem(songUiState) {
                songListViewModel.onSongClicked(songUiState)
            }
        }
    }
}

@Composable
fun SongItem(
    songUiState: SongUiState,
    onSongClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSongClicked()
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(songUiState.albumArtUri)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = songUiState.title,
                fontSize = 16.sp
            )

            Text(
                text = songUiState.artist ?: "",
                color = defaultTextColor,
                fontSize = 14.sp
            )
        }
        if (songUiState.isPlaying != null) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                painter = if (songUiState.isPlaying!!)
                    painterResource(id = R.drawable.ic_pause) else
                    painterResource(id = R.drawable.ic_play),
                contentDescription = null
            )
        }
    }
}
