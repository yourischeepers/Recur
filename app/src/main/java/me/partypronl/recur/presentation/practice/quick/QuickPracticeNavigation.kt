package me.partypronl.recur.presentation.practice.quick

sealed interface QuickPracticeNavigation {

    data object OpenDecks : QuickPracticeNavigation
    data object OpenAccount: QuickPracticeNavigation
}
