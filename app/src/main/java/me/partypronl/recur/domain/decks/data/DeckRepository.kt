package me.partypronl.recur.domain.decks.data

import kotlinx.coroutines.flow.Flow
import me.partypronl.recur.domain.decks.model.Deck

interface DeckRepository {

    fun observeDecks(): Flow<List<Deck>>
    suspend fun createDeck(name: String)
    suspend fun updateDeck(deck: Deck)
    suspend fun deleteDeck(deck: Deck)
}
