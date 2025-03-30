package me.partypronl.recur.presentation.decks.model

import androidx.compose.runtime.Immutable
import me.partypronl.recur.domain.decks.model.Deck

@Immutable
data class DeckUIModel(
    val deck: Deck,
    val name: String,
    val knowledgePercentage: Double,
    val amountOfCards: Int,
    val previewText: String,
)
