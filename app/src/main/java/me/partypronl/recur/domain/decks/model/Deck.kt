package me.partypronl.recur.domain.decks.model

import kotlinx.datetime.Instant
import java.util.UUID

data class Deck(
    val id: UUID,
    val name: String,
    val cards: List<FlashCard>,
    val createdAt: Instant,
) {

    val knowledgePercentage = if (cards.isEmpty()) {
        0.0
    } else {
        cards.sumOf { it.knowledgePercentage } / cards.size.toDouble()
    }
}
