package me.partypronl.recur.domain.decks

import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class CreateCard(
    private val deckRepository: DeckRepository,
) {

    suspend operator fun invoke(
        deck: Deck,
        front: String,
        back: String,
    ) {
        val newCards = deck.cards.toMutableList()
        newCards.add(
            FlashCard(
                id = UUID.randomUUID(),
                front = front.trim(),
                back = back.trim(),
            )
        )

        val newDeck = deck.copy(cards = newCards)
        deckRepository.updateDeck(newDeck)
    }
}
