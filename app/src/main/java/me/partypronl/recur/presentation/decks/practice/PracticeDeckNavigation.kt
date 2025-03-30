package me.partypronl.recur.presentation.decks.practice

sealed interface PracticeDeckNavigation {

    data object GoBack : PracticeDeckNavigation
}
