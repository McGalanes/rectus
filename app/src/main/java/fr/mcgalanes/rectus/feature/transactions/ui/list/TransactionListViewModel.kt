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
class TransactionListViewModel @Inject constructor(
    private val getTransactions: GetTransactionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScreenUiState>(ScreenUiState.Loading)
    val uiState: StateFlow<ScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTransactions()
                .onSuccess { _uiState.value = ScreenUiState.Transactions(it) }
                .onFailure { _uiState.value = ScreenUiState.Error }
        }
    }

    sealed interface ScreenUiState {
        data class Transactions(val transactions: List<Transaction>) : ScreenUiState
        object Loading : ScreenUiState
        object Error : ScreenUiState
    }
}