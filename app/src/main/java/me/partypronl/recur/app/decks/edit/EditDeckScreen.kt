package me.partypronl.recur.app.decks.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.partypronl.recur.R
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.presentation.decks.edit.EditDeckArgs
import me.partypronl.recur.presentation.decks.edit.EditDeckNavigation
import me.partypronl.recur.presentation.decks.edit.EditDeckViewModel
import me.partypronl.recur.presentation.decks.edit.model.EditDeckCardUIModel
import me.partypronl.recur.presentation.decks.edit.model.EditDeckUIModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditDeckScreen(
    deck: Deck,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EditDeckViewModel = koinViewModel(
        parameters = { parametersOf(EditDeckArgs(deck)) }
    )
) {
    val uiModel by viewModel.uiModel.collectAsState()
    viewModel.navigation.HandleNavigation(navController)

    var createCardDialogOpen by remember { mutableStateOf(false) }

    EditDeckContent(
        uiModel = uiModel,
        onClickCreateCard = { createCardDialogOpen = true },
        onClickImportCards = viewModel::onImportCardsClicked,
        onClickBack = viewModel::onBackClicked,
        onClickDeleteDeck = viewModel::onDeleteDeckClicked,
        onClickDeleteCard = viewModel::onDeleteCardClicked,
        modifier = modifier,
    )

    if (createCardDialogOpen) {
        CreateCardDialog(
            deck = deck,
            onDismissRequest = { createCardDialogOpen = false },
            modifier = Modifier.fillMaxSize(),
        )
    }

    if (uiModel.cardToDelete != null) {
        DeleteCardConfirmationDialog(
            isDeleting = uiModel.isDeletingCard,
            onClickConfirm = viewModel::onDeleteCardConfirm,
            onDismissRequest = viewModel::onDismissDeleteCard,
        )
    }

    if (uiModel.deleteDeckDialogOpen) {
        DeleteDeckConfirmationDialog(
            isDeleting = uiModel.isDeletingDeck,
            onClickConfirm = viewModel::onDeleteDeckConfirm,
            onDismissRequest = viewModel::onDismissDeleteDeck,
        )
    }

    if (uiModel.importCardsDialogOpen) {
        ImportCardsDialog(
            deck = deck,
            onDismissRequest = viewModel::onDismissImportCards,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun EditDeckContent(
    uiModel: EditDeckUIModel,
    onClickCreateCard: () -> Unit,
    onClickImportCards: () -> Unit,
    onClickBack: () -> Unit,
    onClickDeleteDeck: () -> Unit,
    onClickDeleteCard: (EditDeckCardUIModel) -> Unit,
    modifier: Modifier = Modifier,
) = Scaffold(
    modifier = modifier,
    topBar = {
        EditDeckTopBar(
            deckName = uiModel.name,
            onClickBack = onClickBack,
            onClickEditName = {}, // TODO
            onClickDelete = onClickDeleteDeck,
        )
    }
) { innerPadding ->
    CardsList(
        uiModel = uiModel,
        onClickImportCards = onClickImportCards,
        onClickCreateCard = onClickCreateCard,
        onClickDeleteCard = onClickDeleteCard,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
    )
}

@Composable
private fun CardsList(
    uiModel: EditDeckUIModel,
    onClickImportCards: () -> Unit,
    onClickCreateCard: () -> Unit,
    onClickDeleteCard: (EditDeckCardUIModel) -> Unit,
    modifier: Modifier = Modifier,
) = LazyColumn(
    verticalArrangement = Arrangement.spacedBy(4.dp),
    modifier = modifier
        .padding(
            horizontal = 12.dp,
            vertical = 8.dp,
        ),
) {
    item {
        CardsListHeader(
            amountOfCards = uiModel.amountOfCards,
            onClickAdd = onClickCreateCard,
            modifier = Modifier.fillMaxWidth(),
        )
    }

    items(uiModel.cards) {
        Card(
            uiModel = it,
            onClickDelete = { onClickDeleteCard(it) },
            modifier = Modifier.fillMaxWidth(),
        )
    }

    item {
        AddCards(
            onClickCreateCard = onClickCreateCard,
            onClickImportCards = onClickImportCards,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun AddCards(
    onClickCreateCard: () -> Unit,
    onClickImportCards: () -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .clip(shape = MaterialTheme.shapes.large)
        .background(color = MaterialTheme.colorScheme.surfaceContainerLowest),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                width = 1.dp,
                shape = MaterialTheme.shapes.large.copy(
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp),
                ),
            )
            .clickable { onClickCreateCard() }
            .weight(1F)
            .padding(
                horizontal = 8.dp,
                vertical = 12.dp,
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = "Add card", // TODO
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                width = 1.dp,
                shape = MaterialTheme.shapes.large.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                ),
            )
            .clickable { onClickImportCards() }
            .weight(1F)
            .padding(
                horizontal = 8.dp,
                vertical = 12.dp,
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_download_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = "Import cards", // TODO
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun Card(
    uiModel: EditDeckCardUIModel,
    onClickDelete: () -> Unit,
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
            start = 12.dp,
            end = 12.dp,
            top = 8.dp,
            bottom = 16.dp,
        )
    ) {
        LinearProgressIndicator(
            progress = { uiModel.knowledgePercentage.toFloat() },
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

        Text(
            text = uiModel.front,
            style = MaterialTheme.typography.titleLarge,
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        Text(
            text = uiModel.back,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
        )

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = if (uiModel.practiceIn != null) {
                    "Practice in ${uiModel.practiceIn}"
                } else {
                    "Ready for practice"
                }, // TODO
            )

            Spacer(modifier = Modifier.weight(1F))

            OutlinedIconButton(
                onClick = onClickDelete,
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "Delete card", // TODO
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            FilledTonalIconButton(
                onClick = {}, // TODO
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_edit_24),
                    contentDescription = "Edit card", // TODO
                )
            }
        }
    }
}

@Composable
private fun CardsListHeader(
    amountOfCards: Int,
    onClickAdd: () -> Unit,
    modifier: Modifier,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Cards", // TODO
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = stringResource(R.string.decks_list_title_amount_indicator, amountOfCards),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }

    IconButton(
        onClick = onClickAdd,
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_add_24),
            contentDescription = "Add card", // TODO
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditDeckTopBar(
    deckName: String,
    onClickBack: () -> Unit,
    onClickEditName: () -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier,
) = TopAppBar(
    title = {
        Text(text = deckName)
    },
    navigationIcon = {
        IconButton(
            onClick = onClickBack,
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Go back", // TODO
            )
        }
    },
    actions = {
        Row {
            IconButton(
                onClick = onClickEditName,
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_edit_24),
                    contentDescription = "Rename", // TODO
                )
            }

            IconButton(
                onClick = onClickDelete,
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "Delete", // TODO
                )
            }
        }
    },
    modifier = modifier,
)

@Composable
private fun EventFlow<EditDeckNavigation>.HandleNavigation(navController: NavController) {
    RetrieveAsEffect {
        when (it) {
            is EditDeckNavigation.GoBack -> navController.popBackStack()
        }
    }
}
