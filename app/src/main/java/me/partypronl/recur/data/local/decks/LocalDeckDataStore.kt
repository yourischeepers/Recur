package me.partypronl.recur.data.local.decks

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import me.partypronl.recur.data.core.decks.DeckDataStore
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.domain.decks.model.FlashCard
import me.partypronl.recur.domain.decks.model.FlashCardResult
import me.partypronl.recur.domain.decks.model.RememberingLevel
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class LocalDeckDataStore : DeckDataStore {

    override fun getDecks(): Flow<List<Deck>> {
        return flowOf(
            listOf(
                Deck(
                    id = UUID.randomUUID(),
                    name = "Test",
                    cards = listOf(
                        FlashCard(
                            id = UUID.randomUUID(),
                            front = "Chinese",
                            back = "English",
                            normalResult = FlashCardResult.default().copy(
                                rememberingLevel = RememberingLevel.TEN,
                            ),
                            reverseResult = FlashCardResult.default().copy(
                                rememberingLevel = RememberingLevel.SEVEN,
                            ),
                        )
                    ),
                    createdAt = Clock.System.now(),
                ),
                Deck(
                    id = UUID.randomUUID(),
                    name = "Test2",
                    cards = listOf(
                        FlashCard(
                            id = UUID.randomUUID(),
                            front = "Chinese",
                            back = "English",
                        ),
                        FlashCard(
                            id = UUID.randomUUID(),
                            front = "FJKLJFJDj;dklsaja",
                            back = "English",
                        ),
                        FlashCard(
                            id = UUID.randomUUID(),
                            front = "FJKLJFJDj;dklsaja",
                            back = "English",
                        ),
                        FlashCard(
                            id = UUID.randomUUID(),
                            front = "FJKLJFJDj;dklsaja",
                            back = "English",
                        ),
                        FlashCard(
                            id = UUID.randomUUID(),
                            front = "FJKLJFJDj;dklsaja",
                            back = "English",
                        ),
                        FlashCard(
                            id = UUID.randomUUID(),
                            front = "FJKLJFJDj;dklsaja",
                            back = "English",
                        )
                    ),
                    createdAt = Clock.System.now(),
                )
            )
        )
    }
}
