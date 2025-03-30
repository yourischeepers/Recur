package me.partypronl.recur.app.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.partypronl.recur.domain.decks.model.CardToPractice
import me.partypronl.recur.presentation.practice.PracticeCardsArgs
import me.partypronl.recur.presentation.practice.PracticeCardsViewModel
import me.partypronl.recur.presentation.practice.model.PracticeCardsUIModel
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
        modifier = modifier,
    )
}

@Composable
private fun CardPracticingContent(
    uiModel: PracticeCardsUIModel?,
    modifier: Modifier = Modifier,
) = Column(modifier = modifier) {

    if (uiModel != null) {
        CardContent(
            frontText = uiModel.frontText,
            backText = uiModel.backText,
            modifier = Modifier.fillMaxSize(),
        )
    } else {
        // TODO Nothing to practice content
    }
}

@Composable
private fun CardContent(
    frontText: String,
    backText: String?,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
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

    backText?.let {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        Text(
            text = it,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
