package tigran.applications.core.util

import tigran.applications.core.navigation.Screen

sealed class UiEvent {
    data class Navigate(val screen: Screen) : UiEvent()
    object NavigateUp : UiEvent()
}