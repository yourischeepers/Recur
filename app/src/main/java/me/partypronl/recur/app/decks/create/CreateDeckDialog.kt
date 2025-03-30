package me.partypronl.recur.app.decks.create

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import me.partypronl.recur.R
import me.partypronl.recur.app.generic.composable.FullScreenDialog
import me.partypronl.recur.app.generic.composable.FullScreenDialogTopBar
import me.partypronl.recur.presentation.decks.create.CreateDeckNavigation
import me.partypronl.recur.presentation.decks.create.CreateDeckViewModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateDeckDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateDeckViewModel = koinViewModel(),
) {
    val uiModel by viewModel.uiModel.collectAsState()
    viewModel.navigation.HandleNavigation(onDismissRequest)

    CreateDeckContent(
        canCreateDeck = uiModel.canCreateDeck && !uiModel.isCreating,
        currentNameInput = uiModel.nameInput,
        onNameInput = viewModel::setNameInput,
        onClickCreate = viewModel::onCreateClicked,
        onClickBack = viewModel::onBackClicked,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    )
}

@Composable
private fun CreateDeckContent(
    canCreateDeck: Boolean,
    currentNameInput: String,
    onNameInput: (String) -> Unit,
    onClickCreate: () -> Unit,
    onClickBack: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) = FullScreenDialog(
    modifier = modifier,
    onDismissRequest = onDismissRequest,
    topBar = {
        FullScreenDialogTopBar(
            title = {
                Text(text = stringResource(R.string.create_deck_title))
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
                    enabled = canCreateDeck,
                    onClick = onClickCreate,
                ) {
                    Text(text = stringResource(R.string.create_deck_finish_button))
                }
            }
        )
    }
) {
    OutlinedTextField(
        value = currentNameInput,
        onValueChange = onNameInput,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = stringResource(R.string.create_deck_name_input_label))
        }
    )
}

@Composable
private fun EventFlow<CreateDeckNavigation>.HandleNavigation(
    onDismissRequest: () -> Unit,
) {
    RetrieveAsEffect {
        when (it) {
            is CreateDeckNavigation.GoBack -> onDismissRequest()
        }
    }
}
