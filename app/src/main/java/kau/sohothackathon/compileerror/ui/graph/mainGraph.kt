package kau.sohothackathon.compileerror.ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.sohothackathon.compileerror.ui.address.AddressScreen
import kau.sohothackathon.compileerror.ui.file.FileScreen
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.vedio.VedioCallScreen
import kau.sohothackathon.compileerror.ui.voice.VoiceCallScreen
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_ROUTE
import kau.sohothackathon.compileerror.util.Constants.FILE_ROUTE
import kau.sohothackathon.compileerror.util.Constants.MAIN_GRAPH
import kau.sohothackathon.compileerror.util.Constants.VIDEO_CALL_ROUTE
import kau.sohothackathon.compileerror.util.Constants.VOICE_CALL_ROUTE

fun NavGraphBuilder.mainGraph(appState: ApplicationState) {
    navigation(
        route = MAIN_GRAPH,
        startDestination = ADDRESS_ROUTE
    ) {
        composable(ADDRESS_ROUTE) { backStackentry ->
            val backEntry = rememberNavControllerBackEntry(backStackentry, appState, MAIN_GRAPH)
            AddressScreen(appState, hiltViewModel(backEntry))
        }
        composable(VOICE_CALL_ROUTE) { backStackentry ->
            val backEntry = rememberNavControllerBackEntry(backStackentry, appState, MAIN_GRAPH)
            VoiceCallScreen(appState)
        }
        composable(VIDEO_CALL_ROUTE) {
            VedioCallScreen(appState)
        }
        composable(FILE_ROUTE) { backStackentry ->
            val backEntry = rememberNavControllerBackEntry(backStackentry, appState, MAIN_GRAPH)
            FileScreen(appState, hiltViewModel(backEntry))
        }

    }
}


@Composable
private fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    appState: ApplicationState,
    graph: String,
) = remember(entry) {
    appState.navController.getBackStackEntry(graph)
}