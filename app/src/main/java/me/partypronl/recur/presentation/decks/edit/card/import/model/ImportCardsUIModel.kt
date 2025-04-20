package me.partypronl.recur.presentation.decks.edit.card.import.model

import androidx.compose.runtime.Immutable

@Immutable
data class ImportCardsUIModel(
    val input: String = "",
    val canImport: Boolean = false,
    val isImporting: Boolean = false,
    val showErrorMessage: Boolean = false,
)
