package fr.mcgalanes.rectus.feature.transactions.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import fr.mcgalanes.rectus.core.common.formatter.toFullDateWithTimeString
import fr.mcgalanes.rectus.core.ui.component.VerticalSpace
import fr.mcgalanes.rectus.core.ui.theme.DarkPurple
import fr.mcgalanes.rectus.core.ui.theme.Gray
import fr.mcgalanes.rectus.core.ui.theme.Orange90
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.ui.TransactionPriceLabel

@Destination
@Composable
fun TransactionDetailRoute(transaction: Transaction) {
    TransactionDetailScreen(transaction)
}

@Composable
fun TransactionDetailScreen(
    transaction: Transaction,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalSpace(36.dp)

        Image(
            painter = rememberAsyncImagePainter(transaction.thumbUrl),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(Orange90)
        )

        VerticalSpace(24.dp)

        TransactionPriceLabel(
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Medium,
            priceInDecimal = transaction.priceInDecimal,
        )

        VerticalSpace(height = 8.dp)

        Text(
            text = transaction.title,
            style = MaterialTheme.typography.bodyMedium,
            color = DarkPurple,
            fontWeight = FontWeight.Bold,
        )

        VerticalSpace(height = 8.dp)

        Text(
            text = transaction.date.toFullDateWithTimeString(),
            style = MaterialTheme.typography.bodyLarge,
            color = Gray,
        )
    }
}
