package kau.sohothackathon.compileerror.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.util.Constants.MAIN_GRAPH
import kau.sohothackathon.compileerror.util.Constants.ONBOARD_GRAPH
import kau.sohothackathon.compileerror.util.Constants.SPLASH_ROUTE
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(appState: ApplicationState) {

    fun navigateToOnboard() {
        appState.navController.navigate(ONBOARD_GRAPH) {
            popUpTo(SPLASH_ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToMainGraph() {
        appState.navController.navigate(MAIN_GRAPH) {
            popUpTo(SPLASH_ROUTE) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(500L)
        navigateToMainGraph()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush =
                Brush.linearGradient(
                    colors = listOf(
                        Color(0x6055A1F8),
                        Color(0x60E7E1FF),
                    ),
                )
            )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "IC_LAUNCHER_FOREGROUND",
                modifier = Modifier.size(240.dp),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.img_deep_truth_logo),
                contentDescription = "IMG_DEEP_TRUTH_LOGO",
                modifier = Modifier.size(180.dp, 34.dp),
                contentScale = ContentScale.Crop
            )
        }

        Image(
            painter = painterResource(id = R.drawable.img_sub_title_logo),
            contentDescription = "IMG_SUB_TITLE_LOGO",
            modifier = Modifier
                .size(180.dp, 150.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 70.dp),
            contentScale = ContentScale.Crop
        )


    }
}