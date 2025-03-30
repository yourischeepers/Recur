package me.partypronl.recur.data.core.decks

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.Deck
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class DeckRepositoryImpl(
    private val dataStore: DeckDataStore,
) : DeckRepository {

    override fun observeDecks(): Flow<List<Deck>> {
        return dataStore.getDecks()
    }

    override suspend fun createDeck(name: String) {
        dataStore.createDeck(
            Deck(
                id = UUID.randomUUID(),
                name = name,
                createdAt = Clock.System.now(),
                cards = emptyList(),
            )
        )
    }
}
