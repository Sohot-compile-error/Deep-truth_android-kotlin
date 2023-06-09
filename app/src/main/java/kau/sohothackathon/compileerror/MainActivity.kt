package kau.sohothackathon.compileerror

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import kau.sohothackathon.compileerror.ui.RootNavhost
import kau.sohothackathon.compileerror.ui.components.ManageBottomBarState
import kau.sohothackathon.compileerror.ui.components.rememberApplicationState
import kau.sohothackathon.compileerror.ui.theme.CompileerrorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val appState = rememberApplicationState()
            appState.uiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
            appState.uiController.setNavigationBarColor(Color.Transparent)
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            CompileerrorTheme {
                ManageBottomBarState(navBackStackEntry, appState)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    RootNavhost(appState)
                }
            }
        }
    }

    private fun checkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
        )
        if (
            permissions.any {
                ContextCompat.checkSelfPermission(
                    this,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            }
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1004
            )
        }
    }

    companion object {
        private val REQUEST_RECORD_AUDIO = 13
        private val AUDIO_LEN_IN_SECOND = 5
        val SAMPLE_RATE = 16000
        val RECORDING_LENGTH = SAMPLE_RATE * AUDIO_LEN_IN_SECOND
        private val LOG_TAG = MainActivity::class.java.simpleName
    }
}