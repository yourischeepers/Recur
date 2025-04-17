package me.partypronl.recur.domain.decks

import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class DeleteCard(
    private val deckRepository: DeckRepository,
) {

    suspend operator fun invoke(
        deck: Deck,
        card: FlashCard,
    ) {
        val newDeck = deck.copy(cards = deck.cards.filter { it.id != card.id })
        deckRepository.updateDeck(newDeck)
    }
}
