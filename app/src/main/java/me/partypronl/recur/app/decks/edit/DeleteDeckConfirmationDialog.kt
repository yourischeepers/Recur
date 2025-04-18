package me.partypronl.recur.app.decks.edit

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteDeckConfirmationDialog(
    isDeleting: Boolean,
    onClickConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) = AlertDialog(
    modifier = modifier,
    onDismissRequest = onDismissRequest,
    confirmButton = {
        TextButton(
            onClick = onClickConfirm,
            enabled = !isDeleting,
        ) {
            Text(
                text = "Confirm", // TODO
            )
        }
    },
    dismissButton = {
        TextButton(
            onClick = onDismissRequest,
        ) {
            Text(
                text = "Cancel", // TODO
            )
        }
    },
    title = {
        Text(
            text = "Delete deck?", // TODO
        )
    }
)
