package tigran.applications.musicplayer.navigation

import androidx.navigation.NavController
import tigran.applications.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.screen)
}