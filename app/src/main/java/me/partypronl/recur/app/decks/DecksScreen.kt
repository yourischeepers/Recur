package me.partypronl.recur.app.decks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.partypronl.recur.R
import me.partypronl.recur.app.MainNavGraph
import me.partypronl.recur.app.decks.create.CreateDeckDialog
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBar
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBarItem
import me.partypronl.recur.app.generic.composable.states.GenericError
import me.partypronl.recur.app.generic.composable.states.GenericLoader
import me.partypronl.recur.presentation.decks.DecksNavigation
import me.partypronl.recur.presentation.decks.DecksViewModel
import me.partypronl.recur.presentation.decks.model.DeckUIModel
import me.partypronl.recur.presentation.decks.model.DecksUIModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import me.partypronl.recur.util.mvvm.StateHostContainer
import me.partypronl.recur.util.mvvm.TypedUIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DecksScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DecksViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.navigation.HandleNavigation(navController)

    var createDeckDialogOpen by remember { mutableStateOf(false) }

    DecksContent(
        uiState = uiState,
        onClickCreateDeck = { createDeckDialogOpen = true },
        onClickPractice = viewModel::onOpenPracticeClicked,
        onClickAccount = viewModel::onOpenAccountClicked,
        onClickPracticeDeck = viewModel::onPracticeDeckClicked,
        onClickEditDeck = viewModel::onEditDeckClicked,
        modifier = modifier,
    )

    if (createDeckDialogOpen) {
        CreateDeckDialog(
            onDismissRequest = { createDeckDialogOpen = false },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun DecksContent(
    uiState: TypedUIState<DecksUIModel, Throwable>,
    onClickCreateDeck: () -> Unit,
    onClickPractice: () -> Unit,
    onClickAccount: () -> Unit,
    onClickPracticeDeck: (DeckUIModel) -> Unit,
    onClickEditDeck: (DeckUIModel) -> Unit,
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
    },
    floatingActionButton = {
        if (uiState is TypedUIState.Normal) {
            CreateDeckFAB(
                onClickCreateDeck = onClickCreateDeck,
            )
        }
    }
) { innerPadding ->
    StateHostContainer(
        state = uiState,
        modifier = Modifier.padding(innerPadding),
        normalContent = {
            NormalContent(
                uiModel = it,
                onClickPracticeDeck = onClickPracticeDeck,
                onClickEditDeck = onClickEditDeck,
                modifier = Modifier.fillMaxSize(),
            )
        },
        loadingContent = {
            GenericLoader(modifier = Modifier.fillMaxSize())
        },
        errorContent = {
            GenericError(modifier = Modifier.fillMaxSize())
        },
    )
}

@Composable
private fun NormalContent(
    uiModel: DecksUIModel,
    onClickPracticeDeck: (DeckUIModel) -> Unit,
    onClickEditDeck: (DeckUIModel) -> Unit,
    modifier: Modifier = Modifier,
) = LazyColumn(
    verticalArrangement = Arrangement.spacedBy(4.dp),
    modifier = modifier
        .padding(
            horizontal = 12.dp,
        ),
) {
    item {
        DecksListTitle(
            amountOfDecks = uiModel.amountOfDecks,
        )
    }

    items(uiModel.decks) {
        DeckCard(
            deck = it,
            onClickPractice = { onClickPracticeDeck(it) },
            onClickEdit = { onClickEditDeck(it) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun DeckCard(
    deck: DeckUIModel,
    onClickPractice: () -> Unit,
    onClickEdit: () -> Unit,
    modifier: Modifier = Modifier,
) = Card(
    border = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ),
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ),
    modifier = modifier,
) {
    Column(
        modifier = Modifier.padding(
            horizontal = 12.dp,
            vertical = 8.dp,
        )
    ) {
        LinearProgressIndicator(
            progress = { deck.knowledgePercentage.toFloat() },
            trackColor = MaterialTheme.colorScheme.tertiaryContainer,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 4.dp,
                    bottom = 8.dp,
                )
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = deck.name,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = pluralStringResource(R.plurals.decks_list_card_amount, deck.amountOfCards, deck.amountOfCards),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F),
                modifier = Modifier.padding(
                    top = 2.dp,
                    end = 2.dp,
                )
            )
        }

        Text(
            text = deck.previewText,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(
                bottom = 12.dp,
            )
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButton(
                onClick = onClickEdit,
            ) {
                Text(
                    text = stringResource(R.string.decks_list_item_manage),
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            FilledTonalButton(
                onClick = onClickPractice,
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                enabled = deck.canPractice,
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_auto_awesome_24), // TODO repeat icon
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )

                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

                Text(
                    text = if (deck.repeat) {
                        stringResource(R.string.decks_list_item_repeat)
                    } else {
                        stringResource(R.string.decks_list_item_practice)
                    },
                )
            }
        }
    }
}

@Composable
private fun DecksListTitle(
    amountOfDecks: Int,
    modifier: Modifier = Modifier,
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(4.dp)
) {
    Text(
        text = stringResource(R.string.decks_list_title),
        style = MaterialTheme.typography.headlineMedium,
    )

    Text(
        text = stringResource(R.string.decks_list_title_amount_indicator, amountOfDecks),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
private fun CreateDeckFAB(
    onClickCreateDeck: () -> Unit,
    modifier: Modifier = Modifier,
) = ExtendedFloatingActionButton(
    modifier = modifier,
    onClick = onClickCreateDeck,
) {
    Icon(
        painter = painterResource(R.drawable.outline_note_stack_add_24),
        contentDescription = null,
    )

    Spacer(modifier = Modifier.width(12.dp))

    Text(
        text = stringResource(R.string.decks_list_create_deck),
    )
}

@Composable
private fun EventFlow<DecksNavigation>.HandleNavigation(navController: NavController) {
    RetrieveAsEffect {
        when (it) {
            is DecksNavigation.OpenPractice -> {
                navController.navigate(MainNavGraph.QuickPractice)
            }
            is DecksNavigation.OpenAccount -> {
                // TODO
            }
            is DecksNavigation.OpenPracticeDeck -> {
                navController.navigate(MainNavGraph.PracticeDeck(it.deck))
            }
            is DecksNavigation.OpenEditDeck -> {
                navController.navigate(MainNavGraph.EditDeck(it.deck))
            }
        }
    }
}
