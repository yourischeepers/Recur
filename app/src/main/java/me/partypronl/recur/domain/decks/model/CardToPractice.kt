package me.partypronl.recur.domain.decks.model

import kotlinx.serialization.Serializable

@Serializable
data class CardToPractice(
    val card: FlashCard,
    val reversed: Boolean,
)

fun List<CardToPractice>.getOrdered(): List<CardToPractice> {
    val original = this.toMutableList()
    val orderedByKnowledgeLevel = original.sortedBy { it.card.getResult(it.reversed).rememberingLevel.ordinal }
    val split = orderedByKnowledgeLevel.groupBy { it.card.getResult(it.reversed).rememberingLevel.ordinal }

    val final = mutableListOf<CardToPractice>()
    for (value in split.values) {
        final += value.shuffled()
    }
    return final
}
