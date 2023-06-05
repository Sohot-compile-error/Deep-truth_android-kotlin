
package kau.sohothackathon.compileerror.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_ROUTE
import kau.sohothackathon.compileerror.util.Constants.FILE_ROUTE

/** 바텀 네비게이션에 대한 Visibility를 관리한다. */
@Composable
fun ManageBottomBarState(
    navBackStackEntry: NavBackStackEntry?,
    applicationState: ApplicationState,
) {
    when (navBackStackEntry?.destination?.route) {
        ADDRESS_ROUTE, FILE_ROUTE -> applicationState.bottomBarState.value = true
        else -> applicationState.bottomBarState.value = false
    }
}