package kau.sohothackathon.compileerror.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kau.sohothackathon.compileerror.ui.graph.addressGraph
import kau.sohothackathon.compileerror.ui.graph.onboardGraph
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.splash.SplashScreen
import kau.sohothackathon.compileerror.util.Constants.SPLASH_ROUTE

@Composable
fun RootNavhost(appState: ApplicationState) {
    Scaffold(
        scaffoldState = appState.scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) { _ ->
        Column {
            NavHost(
                navController = appState.navController,
                startDestination = SPLASH_ROUTE,
                modifier = Modifier.weight(1f)
            ) {
                composable(SPLASH_ROUTE) {
                    SplashScreen(appState)
                }
                onboardGraph(appState)
                addressGraph(appState)
            }
        }
    }
}

