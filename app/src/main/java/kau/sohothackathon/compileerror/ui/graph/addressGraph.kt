package kau.sohothackathon.compileerror.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kau.sohothackathon.compileerror.ui.address.AddressScreen
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_GRAPH
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_ROUTE

fun NavGraphBuilder.addressGraph(appState: ApplicationState) {
    navigation(
        route = ADDRESS_GRAPH,
        startDestination = ADDRESS_ROUTE
    ) {
        composable(ADDRESS_ROUTE) {
            AddressScreen(appState)
        }
    }
}