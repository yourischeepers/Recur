package me.partypronl.recur.domain.decks

import kotlinx.coroutines.flow.first
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class CreateCards(
    private val deckRepository: DeckRepository,
    private val observeDeck: ObserveDeck,
) {

    suspend operator fun invoke(
        deckId: UUID,
        cards: List<FlashCard>,
    ) {
        val deck = observeDeck(deckId).first()
        val newCards = deck.cards + cards
        val newDeck = deck.copy(cards = newCards)
        deckRepository.updateDeck(newDeck)
    }
}
