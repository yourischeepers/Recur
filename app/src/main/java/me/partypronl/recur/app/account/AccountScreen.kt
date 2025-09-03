package me.partypronl.recur.app.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.partypronl.recur.R
import me.partypronl.recur.app.MainNavGraph
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBar
import me.partypronl.recur.app.generic.composable.navigation.RecurNavigationBarItem
import me.partypronl.recur.presentation.account.AccountNavigation
import me.partypronl.recur.presentation.account.AccountViewModel
import me.partypronl.recur.util.mvvm.EventFlow
import me.partypronl.recur.util.mvvm.RetrieveAsEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = koinViewModel(),
) {
    viewModel.navigation.HandleNavigation(navController)

    AccountContent(
        onClickPractice = viewModel::onOpenPracticeClicked,
        onClickDecks = viewModel::onOpenDecksClicked,
        modifier = modifier,
    )
}

@Composable
private fun AccountContent(
    onClickPractice: () -> Unit,
    onClickDecks: () -> Unit,
    modifier: Modifier = Modifier,
) = Scaffold(
    modifier = modifier,
    bottomBar = {
        RecurNavigationBar(
            openItem = RecurNavigationBarItem.Account,
            onOpen = {
                when (it) {
                    RecurNavigationBarItem.Practice -> onClickPractice()
                    RecurNavigationBarItem.Decks -> onClickDecks()
                    else -> Unit
                }
            }
        )
    },
) { innerPadding ->
    AccountPage(
        modifier = Modifier.padding(innerPadding),
    )
}

@Composable
private fun AccountPage(
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier.padding(horizontal = 12.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    StreakInformation(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
    )

    StreakTip(modifier = Modifier.fillMaxWidth())
}

@Composable
private fun StreakInformation(
    modifier: Modifier = Modifier,
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
) {
    Column {
        Text(
            text = "122",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 80.sp,
            ),
        )

        Text(
            text = "Day streak",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .offset(y = (-16).dp)
                .padding(start = 8.dp),
        )
    }

    Icon(
        painter = painterResource(R.drawable.baseline_fire_24),
        contentDescription = "Streak extended today", // TODO
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(96.dp),
    )
}

@Composable
private fun StreakTip(
    modifier: Modifier = Modifier,
) = Row(
    modifier = modifier
        .border(
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            width = 1.dp,
            shape = RoundedCornerShape(12.dp),
        )
        .background(
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shape = RoundedCornerShape(12.dp),
        )
        .padding(
            horizontal = 12.dp,
            vertical = 12.dp,
        ),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    Icon(
        painter = painterResource(R.drawable.baseline_lightbulb_24),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
    )

    Text(
        text = "Practice 10 cards every day to extend your streak",
    )
}

@Composable
private fun EventFlow<AccountNavigation>.HandleNavigation(navController: NavController) {
    RetrieveAsEffect {
        when (it) {
            is AccountNavigation.OpenPractice -> {
                navController.navigate(MainNavGraph.QuickPractice)
            }
            is AccountNavigation.OpenDecks -> {
                navController.navigate(MainNavGraph.Decks)
            }
        }
    }
}
