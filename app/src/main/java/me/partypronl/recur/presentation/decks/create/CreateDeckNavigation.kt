package me.partypronl.recur.presentation.decks.create

sealed interface CreateDeckNavigation {

    data object GoBack : CreateDeckNavigation
}
