package me.partypronl.recur.data.local.decks

import me.partypronl.recur.data.local.database.InstantEntityMapper
import me.partypronl.recur.domain.decks.model.Deck
import org.koin.core.annotation.Factory
import java.util.UUID
import me.partypronl.recur.data.local.Deck as DeckEntity
import me.partypronl.recur.data.local.FlashCard as FlashCardEntity

@Factory
class DeckEntityMapper(
    private val instantEntityMapper: InstantEntityMapper,
    private val flashCardEntityMapper: FlashCardEntityMapper,
) {

    fun mapToEntity(deck: Deck): DeckEntity {
        return DeckEntity(
            id = deck.id.toString(),
            name = deck.name,
            createdAt = instantEntityMapper.mapToEntity(deck.createdAt),
        )
    }

    fun mapToModel(entity: DeckEntity, allCardEntities: List<FlashCardEntity>): Deck {
        val matchingCards = allCardEntities.filter { it.id == entity.id }
        val mappedCards = matchingCards.map { flashCardEntityMapper.mapToModel(it) }

        return Deck(
            id = UUID.fromString(entity.id),
            name = entity.name,
            createdAt = instantEntityMapper.mapToModel(entity.createdAt),
            cards = mappedCards,
        )
    }
}
