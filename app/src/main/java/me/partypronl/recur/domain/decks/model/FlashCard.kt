package me.partypronl.recur.domain.decks.model

import android.annotation.SuppressLint
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

data class FlashCard(
    val id: UUID,
    val front: String,
    val back: String,
    val normalResult: FlashCardResult = FlashCardResult.default(),
    val reverseResult: FlashCardResult = FlashCardResult.default(),
)

data class FlashCardResult(
    val lastCompleted: Instant,
    val rememberingLevel: RememberingLevel,
) {

    companion object {

        @SuppressLint("NewApi")
        fun default(): FlashCardResult {
            return FlashCardResult(
                lastCompleted = Clock.System.now(),
                rememberingLevel = RememberingLevel.ONE,
            )
        }
    }
}

enum class RememberingLevel(
    val repeatTime: Duration,
) {

    ONE(1.minutes),
    TWO(5.minutes),
    THREE(30.minutes),
    FOUR(1.hours),
    FIVE(4.hours),
    SIX(12.hours),
    SEVEN(1.days),
    EIGHT(3.days),
    NINE(7.days),
    TEN(14.days),
}
