package me.partypronl.recur.data.core.decks

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import me.partypronl.recur.data.core.decks.json.CardsJsonMapper
import me.partypronl.recur.data.core.decks.json.model.CardsJson
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class DeckRepositoryImpl(
    private val dataStore: DeckDataStore,
    private val cardsJsonMapper: CardsJsonMapper,
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

    override suspend fun updateDeck(deck: Deck) {
        dataStore.updateDeck(deck)
    }

    override suspend fun deleteDeck(deck: Deck) {
        dataStore.deleteDeck(deck)
    }

    override fun parseCardsJson(json: String): List<FlashCard> {
        val jsonObject = Json.decodeFromString<CardsJson>(json)
        return cardsJsonMapper.fromJson(jsonObject)
    }
}
