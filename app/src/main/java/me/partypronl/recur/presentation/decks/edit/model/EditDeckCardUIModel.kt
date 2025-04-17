package me.partypronl.recur.presentation.decks.edit.model

import me.partypronl.recur.domain.decks.model.FlashCard

data class EditDeckCardUIModel(
    val card: FlashCard,
    val front: String,
    val back: String,
    val knowledgePercentage: Double,
    val practiceIn: String?,
)
