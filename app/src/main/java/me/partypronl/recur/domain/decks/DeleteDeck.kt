package me.partypronl.recur.domain.decks

import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.Deck
import org.koin.core.annotation.Factory

@Factory
class DeleteDeck(
    private val deckRepository: DeckRepository,
) {

    suspend operator fun invoke(deck: Deck) {
        deckRepository.deleteDeck(deck)
    }
}
