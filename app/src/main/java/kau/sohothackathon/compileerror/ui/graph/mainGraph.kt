package kau.sohothackathon.compileerror.ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import kau.sohothackathon.compileerror.ui.address.AddressScreen
import kau.sohothackathon.compileerror.ui.file.AudioPlayScreen
import kau.sohothackathon.compileerror.ui.file.FileScreen
import kau.sohothackathon.compileerror.ui.file.VideoPlayScreen
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.vedio.VedioCallScreen
import kau.sohothackathon.compileerror.ui.voice.VoiceCallScreen
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_ROUTE
import kau.sohothackathon.compileerror.util.Constants.AUDIO_PLAY_ROUTE
import kau.sohothackathon.compileerror.util.Constants.FILE_ROUTE
import kau.sohothackathon.compileerror.util.Constants.MAIN_GRAPH
import kau.sohothackathon.compileerror.util.Constants.VIDEO_CALL_ROUTE
import kau.sohothackathon.compileerror.util.Constants.VIDEO_PLAY_ROUTE
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
        composable(
            route = "${AUDIO_PLAY_ROUTE}?name={name}&mediaType={mediaType}&contentUri={contentUri}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("mediaType") {
                    type = NavType.StringType
                },
                navArgument("contentUri") {
                    type = NavType.StringType
                })
        ) {
            val name = it.arguments?.getString("name")
            val mediaType = it.arguments?.getString("mediaType")
            val contrntUri = it.arguments?.getString("contentUri")

            val backEntry = rememberNavControllerBackEntry(it, appState, MAIN_GRAPH)
            AudioPlayScreen(
                appState,
                hiltViewModel(backEntry),
                name ?: "",
                mediaType ?: "",
                contrntUri ?: ""
            )
        }

        composable(
            route = "${VIDEO_PLAY_ROUTE}?name={name}&mediaType={mediaType}&contentUri={contentUri}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("mediaType") {
                    type = NavType.StringType
                },
                navArgument("contentUri") {
                    type = NavType.StringType
                })
        ) {
            val name = it.arguments?.getString("name")
            val mediaType = it.arguments?.getString("mediaType")
            val contrntUri = it.arguments?.getString("contentUri")

            val backEntry = rememberNavControllerBackEntry(it, appState, MAIN_GRAPH)
            VideoPlayScreen(
                appState,
                hiltViewModel(backEntry),
                name ?: "",
                mediaType ?: "",
                contrntUri ?: ""
            )
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