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
import tigran.applications.musicplayer.main_screen_presentation.MainScreen
import tigran.applications.musicplayer.navigation.navigate


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
                    startDestination = Screen.MainScreen
                ) {
                    composable<Screen.MainScreen> {
                        MainScreen(onNavigate = navController::navigate)
                    }
                }
            }
        }
    }
}
