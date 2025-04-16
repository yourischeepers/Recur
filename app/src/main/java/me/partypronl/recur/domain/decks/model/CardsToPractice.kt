package me.partypronl.recur.domain.decks.model

import kotlinx.datetime.Clock

data class CardsToPractice(
    val value: List<CardToPractice>,
) {

    val createdAt = Clock.System.now()

    fun getOrdered(): List<CardToPractice> {
        val original = value.toMutableList()
        val orderedByKnowledgeLevel = original.sortedBy { it.card.getResult(it.reversed).rememberingLevel.ordinal }
        val split = orderedByKnowledgeLevel.groupBy { it.card.getResult(it.reversed).rememberingLevel.ordinal }

        val final = mutableListOf<CardToPractice>()
        for (value in split.values) {
            final += value.shuffled()
        }
        return final
    }
}

fun List<CardToPractice>.toCardsToPractice(): CardsToPractice {
    return CardsToPractice(this)
}
