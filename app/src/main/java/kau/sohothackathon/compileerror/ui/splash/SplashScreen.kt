package kau.sohothackathon.compileerror.ui.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.util.Constants.ONBOARD_GRAPH
import kau.sohothackathon.compileerror.util.Constants.SPLASH_ROUTE
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(appState: ApplicationState) {

    LaunchedEffect(key1 = Unit) {
        delay(500L)
        appState.navController.navigate(ONBOARD_GRAPH) {
            popUpTo(SPLASH_ROUTE) {
                inclusive = true
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "스플래시 화면")
    }
}