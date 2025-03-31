package me.partypronl.recur.presentation.decks.edit

sealed interface EditDeckNavigation {

    data object GoBack : EditDeckNavigation
}
