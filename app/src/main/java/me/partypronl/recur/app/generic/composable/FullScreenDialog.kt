package me.partypronl.recur.app.generic.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullScreenDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) = Dialog(
    onDismissRequest = onDismissRequest,
    properties = DialogProperties(usePlatformDefaultWidth = false),
) {
    Column(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.surface
        ),
    ) {
        content()
    }
}

@Composable
fun FullScreenDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    topBar: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) = FullScreenDialog(
    onDismissRequest = onDismissRequest,
    modifier = modifier,
) {
    topBar()

    Column(
        modifier = Modifier.padding(
            horizontal = 24.dp,
            vertical = 16.dp,
        )
    ) {
        content()
    }
}

@Composable
fun FullScreenDialogTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) = Row(
    modifier = modifier
        .padding(
            top = 12.dp,
            bottom = 12.dp,
            start = 12.dp,
            end = 12.dp,
        ),
    verticalAlignment = Alignment.CenterVertically,
) {
    navigationIcon()

    Spacer(modifier = Modifier.width(4.dp))

    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.titleLarge) {
        title()
    }

    Spacer(modifier = Modifier.weight(1F))

    Row {
        actions()
    }
}
