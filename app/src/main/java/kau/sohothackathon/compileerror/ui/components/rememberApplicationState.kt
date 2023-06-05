package kau.sohothackathon.compileerror.ui.components

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberApplicationState(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    uiController: SystemUiController = rememberSystemUiController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(Unit) {
    ApplicationState(
        bottomBarState = mutableStateOf(false),
        navController = navController,
        scaffoldState = scaffoldState,
        uiController = uiController,
        coroutineScope = coroutineScope,
    )
}