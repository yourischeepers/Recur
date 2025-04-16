package me.partypronl.recur.presentation.decks.edit

import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.domain.decks.model.FlashCard
import me.partypronl.recur.presentation.decks.edit.model.EditDeckCardUIModel
import me.partypronl.recur.presentation.decks.edit.model.EditDeckUIModel
import org.koin.core.annotation.Factory
import kotlin.time.Duration

@Factory
class EditDeckUIMapper {

    fun toUIModel(deck: Deck): EditDeckUIModel {
        return EditDeckUIModel(
            deck = deck,
            name = deck.name,
            amountOfCards = deck.cards.size,
            cards = deck.cards.map(::toUIModel)
        )
    }

    private fun toUIModel(card: FlashCard): EditDeckCardUIModel {
        return EditDeckCardUIModel(
            card = card,
            front = card.front,
            back = card.back,
            knowledgePercentage = card.knowledgePercentage,
            practiceIn = formatDuration(
                minOf(card.normalResult.timeUntilNextPractice, card.reverseResult.timeUntilNextPractice)
            )
        )
    }

    private fun formatDuration(duration: Duration): String {
        val totalMinutes = duration.inWholeMinutes

        return when {
            totalMinutes >= 1440 -> "${totalMinutes / 1440}d"
            totalMinutes >= 60 -> "${totalMinutes / 60}h"
            else -> "${totalMinutes}m"
        }
    }
}
