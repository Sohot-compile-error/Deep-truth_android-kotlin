package kau.sohothackathon.compileerror.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.util.Constants.MAIN_GRAPH
import kau.sohothackathon.compileerror.util.Screen

/** BottomNavigation Bar를 정의한다. */
@Composable
fun BottomBar(
    appState: ApplicationState,
    bottomNavItems: Array<Screen> = Screen.values(),
) {

    AnimatedVisibility(
        visible = appState.bottomBarState.value,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .navigationBarsPadding(),
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomNavItems.forEachIndexed { _, screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                BottomNavigationItem(
                    icon = {
                        Surface {
                            Icon(
                                painter = painterResource(
                                    id =
                                    (if (isSelected) screen.selectedIcon else screen.unselectedIcon),
                                ),
                                contentDescription = null,
                                tint = if (isSelected) Color(0xFF55A1F8) else Color(0xFFBDBDBD),
                            )
                        }
                    },
                    label = {
                        Text(
                            text = screen.title,
                            color = if (isSelected) Color(0xFF55A1F8) else Color(0xFFBDBDBD),
                            style = MaterialTheme.typography.caption,
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        appState.navController.navigate(screen.route) {
                            popUpTo(MAIN_GRAPH) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = Color.Unspecified,
                    unselectedContentColor = Color.Unspecified,
                )
            }
        }
    }
}