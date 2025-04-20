package me.partypronl.recur.domain.decks.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.partypronl.recur.domain.serialization.UUIDSerializer
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Serializable
data class FlashCard(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val front: String,
    val back: String,
    val normalResult: FlashCardResult = FlashCardResult.default(),
    val reverseResult: FlashCardResult = FlashCardResult.default(),
) {

    val knowledgePercentage = (
            normalResult.knowledgePercentage +
            reverseResult.knowledgePercentage
        ) / 2.0

    fun getResult(isReversed: Boolean): FlashCardResult {
        return if (isReversed) reverseResult else normalResult
    }
}

@Serializable
data class FlashCardResult(
    val lastCompleted: Instant,
    val rememberingLevel: RememberingLevel,
) {

    fun getPracticed(practiceResult: FlashCardPracticeResult): FlashCardResult {
        return FlashCardResult(
            lastCompleted = Clock.System.now(),
            rememberingLevel = RememberingLevel
                .getBounded(rememberingLevel.ordinal + practiceResult.levelsDelta),
        )
    }

    val shouldPractice: Boolean
        get() = lastCompleted + rememberingLevel.repeatTime < Clock.System.now()

    val timeUntilNextPractice: Duration
        get() = lastCompleted + rememberingLevel.repeatTime - Clock.System.now()

    val knowledgePercentage: Double
        get() = if (shouldPractice) {
            rememberingLevel.knowledgePercentage - 1.0 / RememberingLevel.entries.size
        } else {
            rememberingLevel.knowledgePercentage
        }

    companion object {

        fun default(): FlashCardResult {
            return FlashCardResult(
                lastCompleted = Clock.System.now(),
                rememberingLevel = RememberingLevel.ZERO,
            )
        }
    }
}

enum class RememberingLevel(
    val repeatTime: Duration,
) {

    ZERO(0.minutes),
    ONE(1.minutes),
    TWO(5.minutes),
    THREE(30.minutes),
    FOUR(1.hours),
    FIVE(4.hours),
    SIX(12.hours),
    SEVEN(1.days),
    EIGHT(3.days),
    NINE(7.days),
    TEN(14.days);

    val knowledgePercentage: Double
        get() = this.ordinal / (entries.size - 1).toDouble()

    companion object {

        fun getBounded(ordinal: Int): RememberingLevel {
            val bounded = ordinal.coerceIn(0, RememberingLevel.entries.size - 1)
            return entries[bounded]
        }
    }
}
