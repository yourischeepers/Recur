package me.partypronl.recur.data.local.decks

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import me.partypronl.recur.data.core.decks.DeckDataStore
import me.partypronl.recur.data.local.DeckQueries
import me.partypronl.recur.data.local.FlashCardQueries
import me.partypronl.recur.data.local.util.asFlowOfList
import me.partypronl.recur.data.local.util.transactionsOn
import me.partypronl.recur.domain.decks.model.Deck
import org.koin.core.annotation.Factory

@Factory
class LocalDeckDataStore(
    private val deckQueries: DeckQueries,
    private val flashCardQueries: FlashCardQueries,
    private val deckEntityMapper: DeckEntityMapper,
    private val flashCardEntityMapper: FlashCardEntityMapper,
) : DeckDataStore {

    override fun getDecks(): Flow<List<Deck>> {
        return combine(
            deckQueries.selectAll()
                .asFlowOfList(),
            flashCardQueries.selectAll()
                .asFlowOfList()
        ) { deckEntities, flashCardEntities ->
            deckEntities.map { deckEntityMapper.mapToModel(it, flashCardEntities) }
        }
    }

    override suspend fun createDeck(deck: Deck) {
        insertDeck(deck)
    }

    override suspend fun updateDeck(deck: Deck) {
        insertDeck(deck)
    }

    override suspend fun deleteDeck(deck: Deck) {
        val deckEntity = deckEntityMapper.mapToEntity(deck)
        deckQueries.deleteById(deckEntity.id)
        flashCardQueries.deleteByDeckId(deckEntity.id)
    }

    private suspend fun insertDeck(deck: Deck) {
        transactionsOn(
            deckQueries,
            flashCardQueries
        ) {
            val deckEntity = deckEntityMapper.mapToEntity(deck)
            deckQueries.insert(deckEntity)

            flashCardQueries.deleteByDeckId(deckEntity.id)

            val flashCardEntities = deck.cards.map { flashCardEntityMapper.mapToEntity(it, deck) }
            for (flashCardEntity in flashCardEntities) {
                flashCardQueries.insert(flashCardEntity)
            }
        }
    }
}
