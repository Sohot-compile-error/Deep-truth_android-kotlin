package kau.sohothackathon.compileerror.ui.model

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class ApplicationState(
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val uiController: SystemUiController,
    private val coroutineScope: CoroutineScope,
) {

    fun showSnackbar(message: String) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route)
    }
}