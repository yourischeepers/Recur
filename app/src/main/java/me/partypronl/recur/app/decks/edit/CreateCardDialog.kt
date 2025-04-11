package me.partypronl.recur.app.decks.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import me.partypronl.recur.R
import me.partypronl.recur.app.generic.composable.FullScreenDialog
import me.partypronl.recur.app.generic.composable.FullScreenDialogTopBar
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.presentation.decks.edit.card.create.CreateCardArgs
import me.partypronl.recur.presentation.decks.edit.card.create.CreateCardNavigation
import me.partypronl.recur.presentation.decks.edit.card.create.CreateCardViewModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CreateCardDialog(
    onDismissRequest: () -> Unit,
    deck: Deck,
    modifier: Modifier = Modifier,
    viewModel: CreateCardViewModel = koinViewModel(
        parameters = { parametersOf(CreateCardArgs(deck)) }
    )
) {
    val uiModel by viewModel.uiModel.collectAsState()
    viewModel.navigation.HandleNavigation(onDismissRequest)

    CreateCardContent(
        canCreateCard = uiModel.canCreateCard && !uiModel.isCreating,
        currentFrontInput = uiModel.frontInput,
        onFrontInput = viewModel::setFrontInput,
        currentBackInput = uiModel.backInput,
        onBackInput = viewModel::setBackInput,
        onClickCreate = viewModel::onCreateClicked,
        onClickBack = viewModel::onBackClicked,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    )
}

@Composable
private fun CreateCardContent(
    canCreateCard: Boolean,
    currentFrontInput: String,
    onFrontInput: (String) -> Unit,
    currentBackInput: String,
    onBackInput: (String) -> Unit,
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
                Text(text = stringResource(R.string.create_card_title))
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
                    enabled = canCreateCard,
                    onClick = onClickCreate,
                ) {
                    Text(text = stringResource(R.string.create_deck_finish_button))
                }
            }
        )
    }
) {
    OutlinedTextField(
        value = currentFrontInput,
        onValueChange = onFrontInput,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Front")
        }
    )

    OutlinedTextField(
        value = currentBackInput,
        onValueChange = onBackInput,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        label = {
            Text(text = "Back")
        }
    )
}

@Composable
private fun EventFlow<CreateCardNavigation>.HandleNavigation(
    onDismissRequest: () -> Unit,
) {
    RetrieveAsEffect {
        when (it) {
            is CreateCardNavigation.GoBack -> onDismissRequest()
        }
    }
}
