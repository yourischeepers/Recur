package me.partypronl.recur.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import me.partypronl.recur.app.decks.DecksScreen
import me.partypronl.recur.app.decks.practice.PracticeDeckScreen
import me.partypronl.recur.app.practice.quick.QuickPracticeScreen
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.util.serialization.createNavType

@Serializable
data object MainNavGraph {

    @Serializable
    data object Decks

    @Serializable
    data object QuickPractice

    @Serializable
    data class PracticeDeck(val deck: Deck)
}

@Stable
fun NavGraphBuilder.mainRoutes(
    navController: NavController,
) {
    composable<MainNavGraph.Decks> {
        DecksScreen(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
        )
    }

    composable<MainNavGraph.QuickPractice> {
        QuickPracticeScreen(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
        )
    }

    composable<MainNavGraph.PracticeDeck>(
        typeMap = mapOf(createNavType(Deck::class))
    ) {
        PracticeDeckScreen(
            deck = it.toRoute<MainNavGraph.PracticeDeck>().deck,
            navController = navController,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
