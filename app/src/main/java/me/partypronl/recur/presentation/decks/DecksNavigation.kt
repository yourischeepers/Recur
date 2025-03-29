package me.partypronl.recur.presentation.decks

sealed interface DecksNavigation {

    data object OpenPractice : DecksNavigation
    data object OpenAccount : DecksNavigation
}
