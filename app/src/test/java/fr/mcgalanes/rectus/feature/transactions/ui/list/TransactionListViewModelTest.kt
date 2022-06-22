package fr.mcgalanes.rectus.feature.transactions.ui.list

import fr.mcgalanes.rectus.core.testing.rule.MainCoroutineScopeRule
import fr.mcgalanes.rectus.feature.transactions.domain.nextTransactionList
import fr.mcgalanes.rectus.feature.transactions.domain.usecase.GetTransactionsUseCase
import fr.mcgalanes.rectus.feature.transactions.ui.list.TransactionListViewModel.ScreenUiState
import fr.mcgalanes.rectus.feature.transactions.ui.list.TransactionListViewModel.TransactionsUiState
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlin.random.Random
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


@DelicateCoroutinesApi
@OptIn(ExperimentalCoroutinesApi::class)
internal class TransactionListViewModelTest {

    @get:Rule val coroutineScopeRule = MainCoroutineScopeRule()

    private val getTransactions: GetTransactionsUseCase = mockk()

    @Test
    fun `on init should show loading`() = runTest {
        //WHEN
        val viewModel = TransactionListViewModel(getTransactions)

        //THEN
        assertEquals(
            ScreenUiState(transactionsState = TransactionsUiState.Loading),
            viewModel.uiState.first()
        )
    }

    @Test
    fun `on init should show transactions when succeed`() = runTest {
        //GIVEN
        val transactions = Random.nextTransactionList(size = 3)
        coEvery { getTransactions() } returns Result.success(transactions)

        //WHEN
        val viewModel = TransactionListViewModel(getTransactions)

        //THEN
        assertEquals(
            ScreenUiState(transactionsState = TransactionsUiState.Transactions(transactions)),
            viewModel.uiState.first()
        )
    }

    @Test
    fun `on init should show error when fail`() = runTest {
        //GIVEN
        val error = IOException()
        coEvery { getTransactions() } returns Result.failure(error)

        //WHEN
        val viewModel = TransactionListViewModel(getTransactions)

        //THEN
        assertEquals(
            ScreenUiState(transactionsState = TransactionsUiState.Error),
            viewModel.uiState.first()
        )
    }
}