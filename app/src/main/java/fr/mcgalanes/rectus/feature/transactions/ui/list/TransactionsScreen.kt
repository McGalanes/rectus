package fr.mcgalanes.rectus.feature.transactions.ui.list

import androidx.compose.ui.text.intl.Locale.Companion as AndroidLocale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import fr.mcgalanes.rectus.core.ui.theme.DarkPurple
import fr.mcgalanes.rectus.core.ui.theme.Gray
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.ui.list.TransactionsViewModel.TransactionsUiState
import java.time.format.TextStyle
import java.util.Locale

@RootNavGraph(start = true)
@Destination
@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    TransactionList(transactionsState = uiState.transactionsState)
}

@Composable
fun TransactionList(transactionsState: TransactionsUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        when (transactionsState) {
            is TransactionsUiState.Loading -> Unit //TODO

            is TransactionsUiState.Transactions -> {
                items(items = transactionsState.transactions) {
                    TransactionItem(
                        transaction = it,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            TransactionsUiState.Error -> TODO()
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(transaction.thumbUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(18.dp))
                .background(DarkPurple)
        )

        Spacer(modifier = Modifier.width(16.dp))

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

                Text(
                    text = transaction.priceInDecimal.formatPrice(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPurple,
                )
            }

            val currentLocale = AndroidLocale.current
            Text(
                text = transaction.date.run { "$dayOfMonth ${month.getDisplayName(TextStyle.FULL, Locale(currentLocale.language)).lowercase().capitalize(AndroidLocale.current)}" },
                style = MaterialTheme.typography.bodyMedium,
                color = Gray,
            )
        }
    }
}

private fun Double.formatPrice(): String = "$this €"