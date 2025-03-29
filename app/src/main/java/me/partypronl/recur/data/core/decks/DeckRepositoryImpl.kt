package me.partypronl.recur.data.core.decks

import kotlinx.coroutines.flow.Flow
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.Deck
import org.koin.core.annotation.Factory

@Factory
class DeckRepositoryImpl(
    private val dataStore: DeckDataStore,
) : DeckRepository {

    override fun observeDecks(): Flow<List<Deck>> {
        return dataStore.getDecks()
    }
}
