package tigran.applications.musicplayer.current_song_presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
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
    viewModel: CurrentPlayingSongViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onMiniPlayerClicked: () -> Unit,
    getSheetFraction: () -> Float,
) {
    val currentPlayingSongInfo by SongInteractor.currentPlayingSongInfo.collectAsStateWithLifecycle(
        null
    )
    val currentPlayingSongUiState by viewModel.songUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = currentPlayingSongInfo?.id) {
        currentPlayingSongInfo?.let {
            viewModel.getSong(it.id)
        }
    }

    LaunchedEffect(key1 = currentPlayingSongInfo?.isPlaying) {
        currentPlayingSongInfo?.let {
            viewModel.setSongUiStateIsPlaying(it.isPlaying)
        }
    }

    if (currentPlayingSongUiState != null) {
        Box(Modifier.alpha(1f - getSheetFraction())) {
            MiniPlayer(
                songUiState = currentPlayingSongUiState!!,
                onContentClicked = {
                    onMiniPlayerClicked()
                },
                onPlayPauseClicked = {
                    viewModel.onPlayPauseClicked(currentPlayingSongInfo)
                },
            )
        }
        Box(modifier = Modifier.alpha(getSheetFraction())) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                currentPlayingSongUiState?.albumArtUri?.let {
                    SongArt(
                        size = 360.dp,
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
}

@Composable
private fun SongArt(
    size: Dp,
    artUri: String?
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(artUri)
            .build(),
        contentDescription = "",
        modifier = Modifier
            .padding(4.dp)
            .size(size)
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

@Composable
private fun MiniPlayer(
    songUiState: SongUiState,
    onContentClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable {
            onContentClicked()
        }
    ) {
        SongArt(size = 48.dp, artUri = songUiState.albumArtUri)

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = songUiState.title,
                fontSize = 17.sp,
            )

            if (songUiState.artist != null) {
                Text(
                    text = songUiState.artist!!,
                    fontSize = 14.sp,
                    color = defaultTextColor
                )
            }
        }

        if (songUiState.isPlaying != null) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 6.dp)
                    .clickable {
                        onPlayPauseClicked()
                    },
                painter = if (songUiState.isPlaying!!)
                    painterResource(id = R.drawable.ic_pause) else
                    painterResource(id = R.drawable.ic_play),
                contentDescription = null
            )
        }
    }
}