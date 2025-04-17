package me.partypronl.recur.app.practice.quick

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.partypronl.recur.R
import me.partypronl.recur.app.MainNavGraph
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBar
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBarItem
import me.partypronl.recur.app.generic.composable.states.GenericError
import me.partypronl.recur.app.generic.composable.states.GenericLoader
import me.partypronl.recur.app.practice.CardPracticingScreen
import me.partypronl.recur.domain.decks.model.CardsToPractice
import me.partypronl.recur.presentation.practice.quick.QuickPracticeNavigation
import me.partypronl.recur.presentation.practice.quick.QuickPracticeViewModel
import me.partypronl.recur.presentation.practice.quick.model.QuickPracticeUIModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import me.partypronl.recur.util.mvvm.StateHostContainer
import me.partypronl.recur.util.mvvm.TypedUIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuickPracticeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: QuickPracticeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.navigation.HandleNavigation(navController)

    QuickPracticeContent(
        uiState = uiState,
        onClickRepeat = viewModel::onRepeatClicked,
        onClickDecks = viewModel::onOpenDecksClicked,
        onClickAccount = viewModel::onOpenAccountClicked,
        modifier = modifier,
    )
}

@Composable
private fun QuickPracticeContent(
    uiState: TypedUIState<QuickPracticeUIModel, Throwable>,
    onClickRepeat: () -> Unit,
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
    QuickPracticeStates(
        uiState = uiState,
        onClickRepeat = onClickRepeat,
        modifier = Modifier.padding(innerPadding)
    )
}

@Composable
private fun QuickPracticeStates(
    uiState: TypedUIState<QuickPracticeUIModel, Throwable>,
    onClickRepeat: () -> Unit,
    modifier: Modifier = Modifier,
) = StateHostContainer(
    state = uiState,
    loadingContent = {
        GenericLoader(modifier = Modifier.fillMaxSize())
    },
    errorContent = {
        GenericError(modifier = Modifier.fillMaxSize())
    },
    normalContent = {
        QuickPracticeNormalContent(
            uiModel = it,
            onClickRepeat = onClickRepeat,
        )
    },
    modifier = modifier,
)

@Composable
private fun QuickPracticeNormalContent(
    uiModel: QuickPracticeUIModel,
    onClickRepeat: () -> Unit,
    modifier: Modifier = Modifier,
) = CardPracticingScreen(
    cardsToPractice = uiModel.cardsToPractice,
    trackResult = !uiModel.isRepeating,
    finishedButtons = {
        FilledTonalButton(
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            onClick = onClickRepeat,
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_refresh_24),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )

            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

            Text(
                text = "Repeat all cards", // TODO
            )
        }
    },
    modifier = modifier.padding(horizontal = 16.dp),
)

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
