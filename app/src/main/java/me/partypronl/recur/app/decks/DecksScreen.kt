package me.partypronl.recur.app.decks

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBar
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBarItem
import me.partypronl.recur.presentation.decks.DecksNavigation
import me.partypronl.recur.presentation.decks.DecksViewModel
import me.partypronl.recur.util.EventFlow
import me.partypronl.recur.util.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun DecksScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DecksViewModel = koinViewModel(),
) {
    viewModel.navigation.HandleNavigation(navController)

    DecksScreenContent(
        onClickPractice = viewModel::onOpenPracticeClicked,
        onClickAccount = viewModel::onOpenAccountClicked,
        modifier = modifier,
    )
}

@Composable
private fun DecksScreenContent(
    onClickPractice: () -> Unit,
    onClickAccount: () -> Unit,
    modifier: Modifier = Modifier,
) = Scaffold(
    modifier = modifier,
    bottomBar = {
        RecurNavigationBar(
            openItem = RecurNavigationBarItem.Decks,
            onOpen = {
                when (it) {
                    RecurNavigationBarItem.Practice -> onClickPractice()
                    RecurNavigationBarItem.Account -> onClickAccount()
                    else -> Unit
                }
            }
        )
    }
) { innerPadding ->
    Text(
        text = "Decks",
        modifier = Modifier.padding(innerPadding)
    )
}

@Composable
private fun EventFlow<DecksNavigation>.HandleNavigation(navController: NavController) {
    RetrieveAsEffect {
        when (it) {
            is DecksNavigation.OpenPractice -> {
                // TODO
            }
            is DecksNavigation.OpenAccount -> {
                // TODO
            }
        }
    }
}
