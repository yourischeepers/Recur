package me.partypronl.recur.presentation.decks.edit.card.create

sealed interface CreateCardNavigation {

    data object GoBack : CreateCardNavigation
}
