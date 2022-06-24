package fr.mcgalanes.rectus.feature.transactions.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.domain.usecase.GetTransactionsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactions: GetTransactionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ScreenUiState(
            transactionsState = TransactionsUiState.Loading,
            transactionDetailSheetState = TransactionDetailSheetUiState.Hide
        )
    )

    val uiState: StateFlow<ScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val transactionsState =
                getTransactions()
                    .map { TransactionsUiState.Transactions(it) }
                    .getOrElse { TransactionsUiState.Error }

            _uiState.value = ScreenUiState(
                transactionsState = transactionsState,
                transactionDetailSheetState = TransactionDetailSheetUiState.Hide,
            )
        }
    }

    fun onTransactionClick(transaction: Transaction) {
        _uiState.run {
            value = value.copy(
                transactionDetailSheetState = TransactionDetailSheetUiState.Show(transaction)
            )
        }
    }

    data class ScreenUiState(
        val transactionsState: TransactionsUiState,
        val transactionDetailSheetState: TransactionDetailSheetUiState,
    )

    sealed interface TransactionsUiState {
        data class Transactions(val transactions: List<Transaction>) : TransactionsUiState
        object Loading : TransactionsUiState
        object Error : TransactionsUiState
    }

    sealed interface TransactionDetailSheetUiState {
        data class Show(val transaction: Transaction) : TransactionDetailSheetUiState
        object Hide : TransactionDetailSheetUiState
    }
}
