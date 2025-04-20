package me.partypronl.recur.domain.decks

import kotlinx.coroutines.flow.first
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class CreateCard(
    private val deckRepository: DeckRepository,
    private val observeDeck: ObserveDeck,
) {

    suspend operator fun invoke(
        deckId: UUID,
        front: String,
        back: String,
    ) {
        val mostRecentDeck = observeDeck(deckId).first()
        val newCards = mostRecentDeck.cards.toMutableList()
        newCards.add(
            FlashCard(
                id = UUID.randomUUID(),
                front = front.trim(),
                back = back.trim(),
            )
        )

        val newDeck = mostRecentDeck.copy(cards = newCards)
        deckRepository.updateDeck(newDeck)
    }
}
