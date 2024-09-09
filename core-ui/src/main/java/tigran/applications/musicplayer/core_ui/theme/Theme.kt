package tigran.applications.musicplayer.core_ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightThemeColors = lightColors(
    primary = primaryLight,
    primaryVariant = onPrimaryContainerLight,
    secondary = secondaryLight
)

@Composable
fun MusicPlayerTheme(
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = LightThemeColors,
        content = content
    )
}

