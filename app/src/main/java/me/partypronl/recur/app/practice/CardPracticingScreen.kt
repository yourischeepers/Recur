package me.partypronl.recur.app.practice

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.partypronl.recur.R
import me.partypronl.recur.domain.decks.model.CardToPractice
import me.partypronl.recur.domain.decks.model.FlashCardPracticeResult
import me.partypronl.recur.presentation.practice.PracticeCardsArgs
import me.partypronl.recur.presentation.practice.PracticeCardsViewModel
import me.partypronl.recur.presentation.practice.model.PracticeCardsUIModel
import me.partypronl.recur.util.compose.conditionalModifier
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CardPracticingScreen(
    cardsToPractice: List<CardToPractice>,
    modifier: Modifier = Modifier,
    viewModel: PracticeCardsViewModel = koinViewModel(
        parameters = { parametersOf(PracticeCardsArgs(cardsToPractice)) }
    )
) {
    val uiModel by viewModel.uiModel.collectAsState()

    CardPracticingContent(
        uiModel = uiModel,
        onClickRevealBack = viewModel::onRevealBackClicked,
        onClickResultButton = viewModel::onResultButtonClicked,
        modifier = modifier,
    )
}

@Composable
private fun CardPracticingContent(
    uiModel: PracticeCardsUIModel?,
    onClickRevealBack: () -> Unit,
    onClickResultButton: (FlashCardPracticeResult) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier = modifier) {

    if (uiModel != null) {
        CardContent(
            frontText = uiModel.frontText,
            backText = uiModel.backText,
            onClickRevealBack = onClickRevealBack,
            modifier = Modifier.weight(1F),
        )

        CardPracticeResultButtons(
            onClickResultButton = onClickResultButton,
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    bottom = 32.dp,
                ),
        )
    } else {
        // TODO Nothing to practice content or some other kind of end screen that can be inserted?
    }
}

@Composable
private fun CardPracticeResultButtons(
    onClickResultButton: (FlashCardPracticeResult) -> Unit,
    modifier: Modifier = Modifier,
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(4.dp)
) {
    CardPracticeResultButton(
        result = FlashCardPracticeResult.Wrong,
        onClickResultButton = onClickResultButton,
        modifier = Modifier.weight(1F)
    )

    CardPracticeResultButton(
        result = FlashCardPracticeResult.Hard,
        onClickResultButton = onClickResultButton,
        modifier = Modifier.weight(1F)
    )

    CardPracticeResultButton(
        result = FlashCardPracticeResult.Correct,
        onClickResultButton = onClickResultButton,
        modifier = Modifier.weight(1F)
    )

    CardPracticeResultButton(
        result = FlashCardPracticeResult.Easy,
        onClickResultButton = onClickResultButton,
        modifier = Modifier.weight(1F)
    )
}

@Composable
private fun CardPracticeResultButton(
    result: FlashCardPracticeResult,
    onClickResultButton: (FlashCardPracticeResult) -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
        .clip(shape = MaterialTheme.shapes.large)
        .clickable { onClickResultButton(result) }
        .background(
            color = result.getButtonContainerColor(),
        )
        .padding(
            vertical = 8.dp,
            horizontal = 8.dp,
        ),
) {
    Icon(
        painter = painterResource(result.getIcon()),
        contentDescription = null,
        modifier = Modifier.padding(bottom = 4.dp),
    )

    Text(
        text = result.getButtonText(),
        style = MaterialTheme.typography.labelLarge,
    )
}

@Composable
private fun FlashCardPracticeResult.getButtonText(): String {
    return when (this) {
        is FlashCardPracticeResult.Wrong -> stringResource(R.string.card_practicing_result_wrong)

        is FlashCardPracticeResult.Hard -> stringResource(R.string.card_practicing_result_hard)
        is FlashCardPracticeResult.Correct -> stringResource(R.string.card_practicing_result_correct)
        is FlashCardPracticeResult.Easy -> stringResource(R.string.card_practicing_result_easy)
    }
}

@Composable
private fun FlashCardPracticeResult.getButtonContainerColor(): Color {
    return when (this) {
        is FlashCardPracticeResult.Wrong -> MaterialTheme.colorScheme.errorContainer
        is FlashCardPracticeResult.Hard -> MaterialTheme.colorScheme.primaryContainer
        is FlashCardPracticeResult.Correct -> MaterialTheme.colorScheme.secondaryContainer
        is FlashCardPracticeResult.Easy -> MaterialTheme.colorScheme.tertiaryContainer
    }
}

@Composable
@DrawableRes
private fun FlashCardPracticeResult.getIcon(): Int {
    return when (this) {
        is FlashCardPracticeResult.Wrong -> R.drawable.baseline_close_24
        is FlashCardPracticeResult.Hard -> R.drawable.baseline_warning_24
        is FlashCardPracticeResult.Correct -> R.drawable.baseline_check_24
        is FlashCardPracticeResult.Easy -> R.drawable.baseline_done_all_24
    }
}

@Composable
private fun CardContent(
    frontText: String,
    backText: String?,
    onClickRevealBack: () -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
        .clip(
            shape = MaterialTheme.shapes.large,
        )
        .conditionalModifier(backText == null) {
            clickable { onClickRevealBack() }
        }
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            shape = MaterialTheme.shapes.large,
        )
        .background(
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shape = MaterialTheme.shapes.large,
        )
        .padding(
            horizontal = 24.dp,
            vertical = 16.dp,
        ),
) {
    Text(
        text = frontText,
        style = MaterialTheme.typography.titleLarge,
    )

    if (backText != null) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )

        Text(
            text = backText,
            style = MaterialTheme.typography.titleLarge,
        )
    } else {
        Spacer(modifier = Modifier.weight(1F))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_touch_app_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F),
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 12.dp)
            )

            Text(
                text = stringResource(R.string.card_practicing_tap_to_reveal_back),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F),
            )
        }
    }
}
