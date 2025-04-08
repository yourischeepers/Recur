package me.partypronl.recur.domain.decks.model

import android.annotation.SuppressLint
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.partypronl.recur.util.serialization.UUIDSerializer
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
            normalResult.rememberingLevel.knowledgePercentage +
            reverseResult.rememberingLevel.knowledgePercentage
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

    val shouldPractice: Boolean
        get() = Clock.System.now() - rememberingLevel.repeatTime > lastCompleted

    companion object {

        @SuppressLint("NewApi")
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
        get() = this.ordinal / (RememberingLevel.entries.size - 1).toDouble()
}
