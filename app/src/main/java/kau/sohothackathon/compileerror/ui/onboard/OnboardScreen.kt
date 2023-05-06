package kau.sohothackathon.compileerror.ui.onboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.util.Constants.ADDRESS_GRAPH
import kau.sohothackathon.compileerror.util.Constants.ONBOARD_GRAPH

@Composable
fun OnboardScreen(appState: ApplicationState) {

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        Text(text = "온보드 화면")

        Button(onClick = {
            appState.navController.navigate(ADDRESS_GRAPH) {
                popUpTo(ONBOARD_GRAPH) {
                    inclusive = true
                }
            }
        }) {
            Text(text = "주소록화면으로 이동")
        }
    }
}