package fr.mcgalanes.rectus.feature.transactions.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.mcgalanes.rectus.core.common.formatter.toPriceString
import fr.mcgalanes.rectus.core.common.formatter.toShortDateString
import fr.mcgalanes.rectus.core.ui.component.HorizontalSpace
import fr.mcgalanes.rectus.core.ui.theme.DarkPurple
import fr.mcgalanes.rectus.core.ui.theme.Gray
import fr.mcgalanes.rectus.core.ui.theme.Orange90
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.ui.TransactionsViewModel.ScreenUiState
import fr.mcgalanes.rectus.feature.transactions.ui.TransactionsViewModel.TransactionsUiState
import fr.mcgalanes.rectus.feature.transactions.ui.destinations.TransactionDetailRouteDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun TransactionRoute(
    modifier: Modifier = Modifier,
    viewModel: TransactionsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val uiState by viewModel.uiState.collectAsState()

    TransactionsScreen(
        modifier = modifier,
        uiState = uiState,
        onTransactionItemClick = { navigator.navigate(TransactionDetailRouteDestination(it)) },
        onEndScrollReached = viewModel::onEndScrollReached,
    )
}

@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    uiState: ScreenUiState,
    onTransactionItemClick: (Transaction) -> Unit,
    onEndScrollReached: () -> Unit,
) {
    TransactionList(
        modifier = modifier,
        transactionsState = uiState.transactionsState,
        onTransactionItemClick = onTransactionItemClick,
        onEndScrollReached = onEndScrollReached,
    )
}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    transactionsState: TransactionsUiState,
    onTransactionItemClick: (Transaction) -> Unit,
    onEndScrollReached: () -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = transactionsState.transactions) {
            TransactionItem(
                transaction = it,
                onTransactionItemClick = onTransactionItemClick,
            )
        }

        item {
            LoadingItem(modifier)

            LaunchedEffect(key1 = true) {
                onEndScrollReached()
            }
        }
    }
}

@Composable
fun LoadingItem(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {
        CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
    }
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    onTransactionItemClick: (Transaction) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTransactionItemClick(transaction) }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {

        Image(
            painter = rememberAsyncImagePainter(transaction.thumbUrl),
            contentDescription = "transaction icon",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Orange90)
        )

        HorizontalSpace(16.dp)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPurple,
                )

                TransactionPriceLabel(
                    style = MaterialTheme.typography.bodyLarge,
                    priceInDecimal = transaction.priceInDecimal,
                )
            }

            Text(
                text = transaction.date.toShortDateString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Gray,
            )
        }
    }
}

@Composable
fun TransactionPriceLabel(
    modifier: Modifier = Modifier,
    style: TextStyle,
    priceInDecimal: Double,
    fontWeight: FontWeight = FontWeight.Normal,
    showPlusSymbol: Boolean = true,
    color: Color = DarkPurple,
) {
    Text(
        modifier = modifier,
        fontWeight = fontWeight,
        text = priceInDecimal.toPriceString(showPlusSymbol = showPlusSymbol),
        style = style,
        color = color,
    )
}
