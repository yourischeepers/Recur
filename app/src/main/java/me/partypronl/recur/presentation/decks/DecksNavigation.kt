package me.partypronl.recur.presentation.decks

import me.partypronl.recur.domain.decks.model.Deck

sealed interface DecksNavigation {

    data object OpenPractice : DecksNavigation
    data object OpenAccount : DecksNavigation
    data class OpenPracticeDeck(val deck: Deck) : DecksNavigation
    data class OpenEditDeck(val deck: Deck) : DecksNavigation
}
