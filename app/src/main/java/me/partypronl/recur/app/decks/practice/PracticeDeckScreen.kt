package me.partypronl.recur.app.decks.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.partypronl.recur.R
import me.partypronl.recur.app.practice.CardPracticingScreen
import me.partypronl.recur.domain.decks.model.CardToPractice
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.presentation.decks.practice.PracticeDeckArgs
import me.partypronl.recur.presentation.decks.practice.PracticeDeckNavigation
import me.partypronl.recur.presentation.decks.practice.PracticeDeckViewModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PracticeDeckScreen(
    deck: Deck,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PracticeDeckViewModel = koinViewModel(
        parameters = { parametersOf(PracticeDeckArgs(deck)) }
    )
) {
    val cardsToPractice by viewModel.cardsToPractice.collectAsState()
    viewModel.navigation.HandleNavigation(navController)

    PracticeDeckContent(
        cardsToPractice = cardsToPractice,
        onClickBack = viewModel::onBackClicked,
        modifier = modifier.systemBarsPadding(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PracticeDeckContent(
    cardsToPractice: List<CardToPractice>,
    onClickBack: () -> Unit,
    modifier: Modifier,
) = Column(
    modifier = modifier
        .background(
            color = MaterialTheme.colorScheme.surface,
        )
) {
    TopAppBar(
        title = {
            Text(text = "Practicing DECK NAME") // TODO
        },
        navigationIcon = {
            IconButton(
                onClick = onClickBack,
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_close_24),
                    contentDescription = "Close" // TODO
                )
            }
        }
    )

    CardPracticingScreen(
        cardsToPractice = cardsToPractice,
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
            ),
    )
}

@Composable
private fun EventFlow<PracticeDeckNavigation>.HandleNavigation(navController: NavController) {
    RetrieveAsEffect {
        when (it) {
            is PracticeDeckNavigation.GoBack -> navController.popBackStack()
        }
    }
}
