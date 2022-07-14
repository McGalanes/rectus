package fr.mcgalanes.rectus.feature.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.domain.usecase.GetTransactionsUseCase
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionsViewModel
@Inject constructor(
    private val getTransactions: GetTransactionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ScreenUiState(
            transactionsState = TransactionsUiState(transactions = emptyList(), isLoading = true)
        )
    )
    val uiState: StateFlow<ScreenUiState> = _uiState.asStateFlow()

    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun onEndScrollReached() {
        _uiState.update {
            ScreenUiState(
                transactionsState = it.transactionsState.copy(isLoading = true)
            )
        }

        viewModelScope.launch {
            getTransactions()
                .map { transactions ->
                    _uiState.update {
                        ScreenUiState(
                            transactionsState = TransactionsUiState(
                                transactions = it.transactionsState.transactions
                                    .toMutableList()
                                    .apply { addAll(transactions) },
                                isLoading = false,
                            ),
                        )
                    }
                }
                .getOrElse { TODO() }
        }
    }

    fun onTransactionItemClick(transaction: Transaction) {
        viewModelScope.launch {
            _navigationEvents.send(NavigationEvent.ShowTransactionDetail(transaction))
        }
    }

    data class ScreenUiState(val transactionsState: TransactionsUiState)

    data class TransactionsUiState(
        val transactions: List<Transaction> = emptyList(),
        val isLoading: Boolean = false,
    )

    sealed interface NavigationEvent {
        data class ShowTransactionDetail(val transaction: Transaction) : NavigationEvent
    }
}
