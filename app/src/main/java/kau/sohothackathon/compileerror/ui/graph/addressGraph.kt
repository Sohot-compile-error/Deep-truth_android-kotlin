package kau.sohothackathon.compileerror.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.sohothackathon.compileerror.ui.address.AddressScreen
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.vedio.VedioCallScreen
import kau.sohothackathon.compileerror.ui.voice.VoiceCallScreen
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_GRAPH
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_ROUTE
import kau.sohothackathon.compileerror.util.Constants.VIDEO_CALL_ROUTE
import kau.sohothackathon.compileerror.util.Constants.VOICE_CALL_ROUTE

fun NavGraphBuilder.addressGraph(appState: ApplicationState) {
    navigation(
        route = ADDRESS_GRAPH,
        startDestination = ADDRESS_ROUTE
    ) {
        composable(ADDRESS_ROUTE) {
            AddressScreen(appState)
        }
        composable(VOICE_CALL_ROUTE) {
            VoiceCallScreen(appState)
        }
        composable(VIDEO_CALL_ROUTE) {
            VedioCallScreen(appState)
        }
    }
}