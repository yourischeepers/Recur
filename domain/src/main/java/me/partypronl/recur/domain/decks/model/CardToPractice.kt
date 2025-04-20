package me.partypronl.recur.domain.decks.model

import kotlinx.serialization.Serializable

@Serializable
data class CardToPractice(
    val card: FlashCard,
    val reversed: Boolean,
)
