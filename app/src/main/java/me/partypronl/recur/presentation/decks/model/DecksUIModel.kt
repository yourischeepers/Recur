package me.partypronl.recur.presentation.decks.model

import androidx.compose.runtime.Immutable

@Immutable
data class DecksUIModel(
    val amountOfDecks: Int,
    val decks: List<DeckUIModel>,
)
