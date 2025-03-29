package me.partypronl.recur.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MainNavGraph {

    @Serializable
    data object Decks
}

@Stable
fun NavGraphBuilder.mainRoutes(
    navController: NavController,
) {
    composable<MainNavGraph.Decks> {
        Text("Decks")
    }
}
