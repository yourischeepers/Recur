package me.partypronl.recur.domain.decks

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.Deck
import org.koin.core.annotation.Factory

@Factory
class ObserveDecks(
    private val deckRepository: DeckRepository,
) {

    operator fun invoke(): Flow<List<Deck>> {
        return deckRepository.observeDecks()
            .map { decks -> decks.sortedBy { deck -> deck.createdAt }.reversed() }
    }
}
