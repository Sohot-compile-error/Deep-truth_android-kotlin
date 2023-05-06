package kau.sohothackathon.compileerror

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import kau.sohothackathon.compileerror.ui.RootNavhost
import kau.sohothackathon.compileerror.ui.rememberApplicationState
import kau.sohothackathon.compileerror.ui.theme.CompileerrorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkMicPermission()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val appState = rememberApplicationState()
            appState.uiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
            appState.uiController.setNavigationBarColor(Color.Transparent)
            CompileerrorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    RootNavhost(appState)
                }
            }
        }
    }

    private fun checkMicPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1004
            )
        }
    }
}