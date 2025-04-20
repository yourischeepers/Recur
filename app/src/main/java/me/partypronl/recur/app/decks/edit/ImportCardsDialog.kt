package me.partypronl.recur.app.decks.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.partypronl.recur.R
import me.partypronl.recur.app.generic.composable.FullScreenDialog
import me.partypronl.recur.app.generic.composable.FullScreenDialogTopBar
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.presentation.decks.edit.card.import.ImportCardsArgs
import me.partypronl.recur.presentation.decks.edit.card.import.ImportCardsNavigation
import me.partypronl.recur.presentation.decks.edit.card.import.ImportCardsViewModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ImportCardsDialog(
    onDismissRequest: () -> Unit,
    deck: Deck,
    modifier: Modifier = Modifier,
    viewModel: ImportCardsViewModel = koinViewModel(
        parameters = { parametersOf(ImportCardsArgs(deck)) }
    )
) {
    val uiModel by viewModel.uiModel.collectAsState()
    viewModel.navigation.HandleNavigation(onDismissRequest)

    ImportCardsContent(
        canImport = uiModel.canImport && !uiModel.isImporting,
        showError = uiModel.showErrorMessage,
        currentInput = uiModel.input,
        onInput = viewModel::setInput,
        onClickImport = viewModel::onClickConfirm,
        onClickBack = viewModel::onBackClicked,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    )
}

@Composable
private fun ImportCardsContent(
    canImport: Boolean,
    showError: Boolean,
    currentInput: String,
    onInput: (String) -> Unit,
    onClickImport: () -> Unit,
    onClickBack: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) = FullScreenDialog(
    modifier = modifier,
    onDismissRequest = onDismissRequest,
    topBar = {
        FullScreenDialogTopBar(
            title = {
                Text(text = "Import cards") // TODO
            },
            navigationIcon = {
                IconButton(
                    onClick = onClickBack,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = stringResource(R.string.create_deck_back_alt),
                    )
                }
            },
            actions = {
                TextButton(
                    enabled = canImport,
                    onClick = onClickImport,
                ) {
                    Text(text = "Import") // TODO
                }
            }
        )
    }
) {
    OutlinedTextField(
        value = currentInput,
        onValueChange = onInput,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        label = {
            Text(text = "Data") // TODO
        },
        placeholder = {
            Text(
                text = "A JSON object, containing a list 'cards'. Each item in the list is also an object, containing a 'front' and 'back' field with strings." // TODO
            )
        }
    )

    if (showError) {
        Text(
            text = "Parsing failed, is your data correct?", // TODO
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                )
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.large,
                )
                .padding(
                    vertical = 6.dp,
                    horizontal = 12.dp,
                )
        )
    }
}

@Composable
private fun EventFlow<ImportCardsNavigation>.HandleNavigation(
    onDismissRequest: () -> Unit,
) {
    RetrieveAsEffect {
        when (it) {
            is ImportCardsNavigation.GoBack -> onDismissRequest()
        }
    }
}
