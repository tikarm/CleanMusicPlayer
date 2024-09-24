package tigran.applications.musicplayer.main_screen_presentation

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import tigran.applications.core.SongInteractor
import tigran.applications.core.util.UiEvent
import tigran.applications.musicplayer.current_song_presentation.CurrentSongScreen
import tigran.applications.musicplayer.song_list_presentation.SongListScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val currentPlayingSongInfo by SongInteractor.currentPlayingSongInfo.collectAsStateWithLifecycle(
        null
    )

    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 56.dp,
        sheetContent = {
            if (currentPlayingSongInfo != null) {
                CurrentSongScreen(
                    getSheetFraction = scaffoldState::calculateSheetFraction,
                    onMiniPlayerClicked = {
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onNavigate = onNavigate
                )
            }

        }) {
        SongListScreen(
            onNavigate = onNavigate,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun BottomSheetScaffoldState.calculateSheetFraction(): Float {
    val fraction = bottomSheetState.progress
    val targetValue = bottomSheetState.targetValue
    val currentValue = bottomSheetState.currentValue

    return when {
        currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> fraction
        currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 1f - fraction
        currentValue == BottomSheetValue.Expanded -> 1f
        else -> 0f
    }
}