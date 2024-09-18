package tigran.applications.musicplayer.current_song_presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.tigran.applications.MusicPlayer.current_song.presentation.R
import tigran.applications.core.SongInteractor
import tigran.applications.core.util.UiEvent
import tigran.applications.musicplayer.core_ui.theme.defaultTextColor
import tigran.applications.musicplayer.core_ui.util.shimmerEffect
import tigran.applications.musicplayer.song_ui_state.SongUiState

@Composable
fun CurrentSongScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CurrentPlayingSongViewModel = hiltViewModel(),
) {
    val currentPlayingSongInfo by SongInteractor.currentPlayingSongInfo.collectAsStateWithLifecycle(
        null
    )
    val currentPlayingSongUiState by viewModel.songUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = currentPlayingSongInfo?.first) {
        currentPlayingSongInfo?.let {
            viewModel.getSong(it.first)
        }
    }

    LaunchedEffect(key1 = currentPlayingSongInfo?.second) {
        currentPlayingSongInfo?.let {
            viewModel.setSongUiStateIsPlaying(it.second)
        }
    }

    if (currentPlayingSongUiState != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            currentPlayingSongUiState?.albumArtUri?.let {
                SongArt(
                    artUri = it
                )
            }

            PlaybackContent(
                songUiState = currentPlayingSongUiState!!,
            )

            PlaybackButtons(
                songUiState = currentPlayingSongUiState!!,
                onPlayPauseClicked = {
                    viewModel.onPlayPauseClicked(currentPlayingSongInfo)
                },
                onNextSongClicked = viewModel::playNextSong,
                onPreviousSongClicked = viewModel::playPreviousSong
            )
        }
    }
}

@Composable
private fun SongArt(
    artUri: String
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(artUri)
            .build(),
        contentDescription = "",
        modifier = Modifier
            .padding(4.dp)
            .size(360.dp)
            .clip(RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        },
    )
}

@Composable
private fun PlaybackContent(
    songUiState: SongUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = songUiState.title,
            fontSize = 18.sp,
        )

        if (songUiState.artist != null) {
            Text(
                text = songUiState.artist!!,
                color = defaultTextColor
            )
        }
    }
}

@Composable
private fun PlaybackButtons(
    songUiState: SongUiState,
    onPlayPauseClicked: () -> Unit,
    onPreviousSongClicked: () -> Unit,
    onNextSongClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onPreviousSongClicked()
                },
            painter = painterResource(id = R.drawable.ic_skip_previous),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onPlayPauseClicked()
                },
            painter = if (songUiState.isPlaying == true)
                painterResource(id = R.drawable.ic_pause)
            else painterResource(id = R.drawable.ic_play),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onNextSongClicked()
                },
            painter = painterResource(id = R.drawable.ic_skip_next),
            contentDescription = null
        )
    }
}