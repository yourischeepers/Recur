package me.partypronl.recur.presentation.decks.model

import androidx.compose.runtime.Immutable

@Immutable
data class DeckUIModel(
    val name: String,
    val knowledgePercentage: Double,
    val amountOfCards: Int,
    val previewText: String,
)
