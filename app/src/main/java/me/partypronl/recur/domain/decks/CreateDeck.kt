package me.partypronl.recur.domain.decks

import me.partypronl.recur.domain.decks.data.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class CreateDeck(
    private val deckRepository: DeckRepository,
) {

    suspend operator fun invoke(name: String) {
        deckRepository.createDeck(name.trim())
    }
}
