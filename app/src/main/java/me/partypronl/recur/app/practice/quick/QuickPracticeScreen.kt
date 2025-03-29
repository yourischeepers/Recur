package me.partypronl.recur.app.practice.quick

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import me.partypronl.recur.app.MainNavGraph
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBar
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBarItem
import me.partypronl.recur.presentation.practice.quick.QuickPracticeNavigation
import me.partypronl.recur.presentation.practice.quick.QuickPracticeViewModel
import me.partypronl.recur.util.EventFlow
import me.partypronl.recur.util.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuickPracticeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: QuickPracticeViewModel = koinViewModel(),
) {
    viewModel.navigation.HandleNavigation(navController)

    QuickPracticeContent(
        onClickDecks = viewModel::onOpenDecksClicked,
        onClickAccount = viewModel::onOpenAccountClicked,
        modifier = modifier,
    )
}

@Composable
private fun QuickPracticeContent(
    onClickDecks: () -> Unit,
    onClickAccount: () -> Unit,
    modifier: Modifier = Modifier,
) = Scaffold(
    modifier = modifier,
    bottomBar = {
        RecurNavigationBar(
            openItem = RecurNavigationBarItem.Practice,
            onOpen = {
                when (it) {
                    RecurNavigationBarItem.Decks -> onClickDecks()
                    RecurNavigationBarItem.Account -> onClickAccount()
                    else -> Unit
                }
            }
        )
    }
) { innerPadding ->
    Text(
        text = "Quick practice",
        modifier = Modifier.padding(innerPadding)
    )
}

@Composable
private fun EventFlow<QuickPracticeNavigation>.HandleNavigation(navController: NavController) {
    RetrieveAsEffect {
        when (it) {
            is QuickPracticeNavigation.OpenDecks -> {
                navController.navigate(MainNavGraph.Decks)
            }
            is QuickPracticeNavigation.OpenAccount -> {
                // TODO
            }
        }
    }
}
