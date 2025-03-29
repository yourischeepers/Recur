package me.partypronl.recur.app.generic.composable.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import me.partypronl.recur.R

enum class RecurNavigationBarItem(
    @DrawableRes val unfilledIconDrawable: Int,
    @DrawableRes val filledIconDrawable: Int,
    @StringRes val itemName: Int,
) {

    Practice(
        unfilledIconDrawable = R.drawable.outline_auto_awesome_24,
        filledIconDrawable = R.drawable.baseline_auto_awesome_24,
        itemName = R.string.navigation_item_practice,
    ),
    Decks(
        unfilledIconDrawable = R.drawable.outline_cards_24,
        filledIconDrawable = R.drawable.baseline_cards_24,
        itemName = R.string.navigation_item_decks,
    ),
    Account(
        unfilledIconDrawable = R.drawable.outline_badge_24,
        filledIconDrawable = R.drawable.baseline_badge_24,
        itemName = R.string.navigation_item_account,
    ),
}

@Composable
fun RecurNavigationBarItem.getIconPainter(active: Boolean): Painter {
    return painterResource(
        id = if (active) this.filledIconDrawable else this.unfilledIconDrawable
    )
}
