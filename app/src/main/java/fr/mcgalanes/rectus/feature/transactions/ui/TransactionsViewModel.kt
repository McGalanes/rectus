package fr.mcgalanes.rectus.feature.transactions.ui

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

    init {
        viewModelScope.launch {
            val transactionsState =
                getTransactions()
                    .map { TransactionsUiState(transactions = it, isLoading = false) }
                    .getOrElse { TODO() }

            _uiState.value = ScreenUiState(transactionsState = transactionsState)
        }
    }

    data class ScreenUiState(val transactionsState: TransactionsUiState)

    data class TransactionsUiState(
        val transactions: List<Transaction>,
        val isLoading: Boolean,
    )
}
