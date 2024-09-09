package tigran.applications.musicplayer.song_list_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import tigran.applications.core.util.UiEvent


@Composable
fun SongListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    songListViewModel: SongListViewModel = hiltViewModel()
) {
    val songListUiState by songListViewModel.songListUiState.collectAsStateWithLifecycle()

    LazyColumn {
        items(songListUiState.size) { index: Int ->
            val songUiState = songListUiState[index]

            SongItem(songUiState) {
                songListViewModel.playSong(songUiState.id)
            }
        }
    }
}

@Composable
fun SongItem(
    songUiState: SongUiState,
    onSongClicked: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
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
                .clickable {
                    onSongClicked()
                }
        ) {
            Text(text = songUiState.title)

            Text(text = songUiState.artist ?: "")
        }
    }
}