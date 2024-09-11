package tigran.applications.musicplayer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tigran.applications.core.navigation.Screen
import tigran.applications.musicplayer.PermissionManager
import tigran.applications.musicplayer.core_ui.theme.MusicPlayerTheme
import tigran.applications.musicplayer.navigation.navigate
import tigran.applications.musicplayer.song_list_presentation.SongListScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionManager = PermissionManager(this) {
            setContent()
        }

        permissionManager.initializePermissionLauncher()

        permissionManager.requestPermissions()
    }

    private fun setContent() {
        setContent {
            MusicPlayerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.SongListScreen
                ) {
                    composable<Screen.SongListScreen> {
                        SongListScreen(onNavigate = navController::navigate)
                    }
                }
            }
        }
    }
}
