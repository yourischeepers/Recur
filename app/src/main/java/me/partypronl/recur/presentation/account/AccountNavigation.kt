package me.partypronl.recur.presentation.account

sealed interface AccountNavigation {

    data object OpenPractice : AccountNavigation
    data object OpenDecks : AccountNavigation
}
