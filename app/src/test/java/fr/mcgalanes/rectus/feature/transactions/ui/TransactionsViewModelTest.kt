package fr.mcgalanes.rectus.feature.transactions.ui

import app.cash.turbine.test
import fr.mcgalanes.rectus.core.testing.rule.TestDispatcherRule
import fr.mcgalanes.rectus.feature.transactions.domain.nextTransactionList
import fr.mcgalanes.rectus.feature.transactions.domain.usecase.GetTransactionsUseCase
import fr.mcgalanes.rectus.feature.transactions.ui.TransactionsViewModel.TransactionsUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.random.Random
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


@DelicateCoroutinesApi
@OptIn(ExperimentalCoroutinesApi::class)
internal class TransactionsViewModelTest {

    @get:Rule val dispatcherRule = TestDispatcherRule()

    private val getTransactions: GetTransactionsUseCase = mockk()
    private fun viewModel() = TransactionsViewModel(getTransactions)

    @Test
    fun `on init should show loading and empty transactions`() = runTest {
        //WHEN
        val viewModel = viewModel()

        //THEN
        viewModel.uiState.test {
            assertEquals(
                TransactionsUiState(transactions = emptyList(), isLoading = true),
                awaitItem().transactionsState,
            )
        }
    }

    @Test
    fun `onEndScrollReached should show loading`() = runTest {
        //GIVEN
        val transactions = Random.nextTransactionList(size = 5).toMutableList()
        coEvery { getTransactions() } returns Result.success(transactions)
        val viewModel = viewModel()

        //WHEN
        viewModel.onEndScrollReached()

        //THEN
        viewModel.uiState.test {
            assertTrue(awaitItem().transactionsState.isLoading)
        }
    }

    @Test
    fun `onEndScrollReached should show transactions when succeed`() = runTest {
        //GIVEN
        val transactions = Random.nextTransactionList(size = 5).toMutableList()
        coEvery { getTransactions() } returns Result.success(transactions)
        val viewModel = viewModel()

        //WHEN
        viewModel.onEndScrollReached()

        //THEN
        viewModel.uiState.test {
            skipItems(1)

            assertEquals(
                TransactionsUiState(
                    transactions = transactions,
                    isLoading = false,
                ),
                awaitItem().transactionsState,
            )
        }
    }
}
