package me.partypronl.recur.data.local.decks

import me.partypronl.recur.data.local.database.InstantEntityMapper
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.domain.decks.model.FlashCard
import me.partypronl.recur.domain.decks.model.FlashCardResult
import me.partypronl.recur.domain.decks.model.RememberingLevel
import org.koin.core.annotation.Factory
import java.util.UUID
import me.partypronl.recur.data.local.FlashCard as FlashCardEntity

@Factory
class FlashCardEntityMapper(
    private val instantEntityMapper: InstantEntityMapper,
) {

    fun mapToEntity(flashCard: FlashCard, deck: Deck): FlashCardEntity {
        return FlashCardEntity(
            id = flashCard.id.toString(),
            deckId = deck.id.toString(),
            front = flashCard.front,
            back = flashCard.back,
            normalLevel = flashCard.normalResult.rememberingLevel.ordinal.toLong(),
            reverseLevel = flashCard.reverseResult.rememberingLevel.ordinal.toLong(),
            normalLastCompleted = instantEntityMapper.mapToEntity(flashCard.normalResult.lastCompleted),
            reverseLastCompleted = instantEntityMapper.mapToEntity(flashCard.reverseResult.lastCompleted),
        )
    }

    fun mapToModel(entity: FlashCardEntity): FlashCard {
        return FlashCard(
            id = UUID.fromString(entity.id),
            front = entity.front,
            back = entity.back,
            normalResult = FlashCardResult(
                lastCompleted = instantEntityMapper.mapToModel(entity.normalLastCompleted),
                rememberingLevel = RememberingLevel.entries[entity.normalLevel.toInt()],
            ),
            reverseResult = FlashCardResult(
                lastCompleted = instantEntityMapper.mapToModel(entity.reverseLastCompleted),
                rememberingLevel = RememberingLevel.entries[entity.reverseLevel.toInt()],
            )
        )
    }
}
