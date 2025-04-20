package me.partypronl.recur.domain.decks

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.partypronl.recur.domain.decks.model.Deck
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class ObserveDeck(
    private val observeDecks: ObserveDecks,
) {

    operator fun invoke(id: UUID): Flow<Deck> {
        return observeDecks()
            .map { it.find { deck -> deck.id == id }!! }
    }
}
