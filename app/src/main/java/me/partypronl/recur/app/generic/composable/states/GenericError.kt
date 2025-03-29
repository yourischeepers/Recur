package me.partypronl.recur.app.generic.composable.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.partypronl.recur.R

@Composable
fun GenericError(
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Icon(
        painter = painterResource(R.drawable.baseline_error_outline_24),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F),
        modifier = Modifier
            .padding(bottom = 24.dp)
            .size(64.dp)
    )

    Text(
        text = stringResource(R.string.unknown_error_occurred),
        style = MaterialTheme.typography.bodyLarge,
    )
}
