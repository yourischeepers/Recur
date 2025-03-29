package me.partypronl.recur.app.generic.composable.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun RecurNavigationBar(
    openItem: RecurNavigationBarItem,
    onOpen: (RecurNavigationBarItem) -> Unit,
    modifier: Modifier = Modifier,
) = NavigationBar(
    modifier = modifier,
) {
    for (item in RecurNavigationBarItem.entries) {
        NavigationBarItem(
            selected = item == openItem,
            onClick = { onOpen(item) },
            icon = {
                Icon(
                    painter = item.getIconPainter(item == openItem),
                    contentDescription = stringResource(item.itemName),
                )
            },
            label = {
                Text(
                    text = stringResource(item.itemName),
                )
            }
        )
    }
}
