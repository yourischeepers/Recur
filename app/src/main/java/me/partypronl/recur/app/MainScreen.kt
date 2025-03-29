package me.partypronl.recur.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    MainContent(
        navController = navController,
        modifier = modifier,
    )
}

@Composable
private fun MainContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MainNavGraph.Decks,
        modifier = modifier,
    ) {
        mainRoutes(navController)
    }
}
