package me.partypronl.recur.domain.decks

import kotlinx.coroutines.flow.first
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class DeleteCard(
    private val deckRepository: DeckRepository,
    private val observeDeck: ObserveDeck,
) {

    suspend operator fun invoke(
        deckId: UUID,
        card: FlashCard,
    ) {
        val mostRecentDeck = observeDeck(deckId).first()
        val newDeck = mostRecentDeck.copy(cards = mostRecentDeck.cards.filter { it.id != card.id })
        deckRepository.updateDeck(newDeck)
    }
}
