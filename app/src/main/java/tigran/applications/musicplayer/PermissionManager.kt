package tigran.applications.musicplayer

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionManager(
    private val activity: ComponentActivity,
    private val onPermissionGranted: () -> Unit
) {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var multiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    fun initializePermissionLauncher() {
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                showSettingsDialog()
            }
        }

        multiplePermissionsLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                onPermissionGranted()
            } else {
                permissions.entries.forEach { entry ->
                    if (!entry.value) {
                        when (entry.key) {
                            Manifest.permission.READ_MEDIA_AUDIO,
                            Manifest.permission.READ_EXTERNAL_STORAGE -> handlePermissionDenied(
                                entry.key
                            )

                            Manifest.permission.POST_NOTIFICATIONS -> handlePermissionDenied(entry.key)
                        }
                    }
                }
            }
        }
    }

    fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            handleMultiplePermissions(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            handlePermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun handleMultiplePermissions(vararg permissions: String) {
        val permissionsToRequest = permissions.filterNot { isPermissionGranted(it) }.toTypedArray()
        if (permissionsToRequest.isNotEmpty()) {
            multiplePermissionsLauncher.launch(permissionsToRequest)
        } else {
            onPermissionGranted()
        }
    }

    private fun handlePermission(permission: String, manifestPermission: String) {
        when {
            isPermissionGranted(permission) -> {
                onPermissionGranted()
            }

            activity.shouldShowRequestPermissionRationale(manifestPermission) -> {
                showPermissionRationale(permission)
            }

            else -> {
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun handlePermissionDenied(permission: String) {
        showSettingsDialog(permission)
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPermissionRationale(permission: String) {
        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("This app requires access to your audio files and notifications to function properly.")
            .setPositiveButton("Grant") { _, _ ->
                permissionLauncher.launch(permission)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showSettingsDialog(permission: String? = null) {
        AlertDialog.Builder(activity)
            .setTitle("Permission Denied")
            .setMessage("You've denied the $permission permission permanently. Please enable it in settings.")
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
