package kau.sohothackathon.compileerror.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.onboard.OnboardScreen
import kau.sohothackathon.compileerror.util.Constants.ONBOARD_GRAPH
import kau.sohothackathon.compileerror.util.Constants.ONBOARD_ROUTE

fun NavGraphBuilder.onboardGraph(appState: ApplicationState) {
    navigation(
        route = ONBOARD_GRAPH,
        startDestination = ONBOARD_ROUTE
    ) {
        composable(ONBOARD_ROUTE) {
            OnboardScreen(appState)
        }
    }
}