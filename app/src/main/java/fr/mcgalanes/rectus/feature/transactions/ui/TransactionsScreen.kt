@file:OptIn(ExperimentalMaterialApi::class)

package fr.mcgalanes.rectus.feature.transactions.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import fr.mcgalanes.rectus.core.common.formatter.toFullDateWithTimeString
import fr.mcgalanes.rectus.core.common.formatter.toPriceString
import fr.mcgalanes.rectus.core.common.formatter.toShortDateString
import fr.mcgalanes.rectus.core.ui.component.HorizontalSpace
import fr.mcgalanes.rectus.core.ui.component.VerticalSpace
import fr.mcgalanes.rectus.core.ui.theme.DarkPurple
import fr.mcgalanes.rectus.core.ui.theme.Gray
import fr.mcgalanes.rectus.core.ui.theme.Orange90
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.ui.TransactionsViewModel.TransactionsUiState
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTransaction = viewModel.selectedTransaction.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            TransactionDetailSheet(selectedTransaction.value)
        }
    ) {
        TransactionList(
            transactionsState = uiState.transactionsState,
            onTransactionItemClick = { transaction ->
                viewModel.onTransactionItemClick(transaction)
                scope.launch { bottomSheetState.show() }
            },
        )
    }
}

@Composable
fun TransactionList(
    transactionsState: TransactionsUiState,
    onTransactionItemClick: (Transaction) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when (transactionsState) {
            is TransactionsUiState.Loading -> Unit //TODO

            is TransactionsUiState.Transactions -> {
                items(items = transactionsState.transactions) {
                    TransactionItem(
                        transaction = it,
                        onTransactionItemClick = onTransactionItemClick,
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
    onTransactionItemClick: (Transaction) -> Unit,
    modifier: Modifier = Modifier
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
    priceInDecimal: Double?,
    fontWeight: FontWeight = FontWeight.Normal,
    showPlusSymbol: Boolean = true,
    color: Color = DarkPurple,
) {
    Text(
        modifier = modifier,
        fontWeight = fontWeight,
        text = priceInDecimal?.toPriceString(showPlusSymbol = showPlusSymbol) ?: "...",
        style = style,
        color = color,
    )
}

@Composable
fun TransactionDetailSheet(transaction: Transaction?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalSpace(36.dp)

        Image(
            painter = rememberAsyncImagePainter(transaction?.thumbUrl),
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
            priceInDecimal = transaction?.priceInDecimal,
        )

        VerticalSpace(height = 8.dp)

        Text(
            text = transaction?.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = DarkPurple,
            fontWeight = FontWeight.Bold,
        )

        VerticalSpace(height = 8.dp)

        Text(
            text = transaction?.date?.toFullDateWithTimeString() ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = Gray,
        )

        VerticalSpace(height = 36.dp)
    }
}
