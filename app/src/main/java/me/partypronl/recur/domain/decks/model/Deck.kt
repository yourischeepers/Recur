package me.partypronl.recur.domain.decks.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.partypronl.recur.util.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class Deck(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val cards: List<FlashCard>,
    val createdAt: Instant,
) {

    fun getCardsToPractice(): List<CardToPractice> {
        val cardsToPractice = mutableListOf<CardToPractice>()

        for (card in cards) {
            if (card.normalResult.shouldPractice) {
                cardsToPractice.add(
                    CardToPractice(
                        card = card,
                        reversed = false,
                    )
                )
            }

            if (card.reverseResult.shouldPractice) {
                cardsToPractice.add(
                    CardToPractice(
                        card = card,
                        reversed = true,
                    )
                )
            }
        }

        return cardsToPractice
    }

    val knowledgePercentage = if (cards.isEmpty()) {
        0.0
    } else {
        cards.sumOf { it.knowledgePercentage } / cards.size.toDouble()
    }
}
