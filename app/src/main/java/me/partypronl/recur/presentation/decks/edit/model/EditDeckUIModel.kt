package me.partypronl.recur.presentation.decks.edit.model

import androidx.compose.runtime.Immutable
import me.partypronl.recur.domain.decks.model.Deck

@Immutable
data class EditDeckUIModel(
    val deck: Deck,
    val name: String,
    val amountOfCards: Int,
    val cards: List<EditDeckCardUIModel>,
    val cardToDelete: EditDeckCardUIModel?,
    val isDeletingCard: Boolean = false,
    val deleteDeckDialogOpen: Boolean = false,
    val isDeletingDeck: Boolean = false,
)
