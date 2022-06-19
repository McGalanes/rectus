package fr.mcgalanes.rectus.feature.transactions.ui.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import fr.mcgalanes.rectus.core.ui.theme.DarkPurple
import fr.mcgalanes.rectus.core.ui.theme.Gray
import fr.mcgalanes.rectus.feature.transactions.ui.model.Transaction

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "screen")
@Composable
fun TransactionListScreen() {
    TransactionList(transactions = emptyList())
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionList(transactions: List<Transaction>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        items(items = transactions) {
            TransactionItem(
                transaction = it,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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

            Text(
                text = transaction.date.run { "$dayOfMonth ${month.name.lowercase().capitalize(Locale.current)}" },
                style = MaterialTheme.typography.bodyMedium,
                color = Gray,
            )
        }
    }
}

private fun Double.formatPrice(): String = "$this €"
