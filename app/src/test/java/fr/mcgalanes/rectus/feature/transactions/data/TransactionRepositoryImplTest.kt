package fr.mcgalanes.rectus.feature.transactions.data

import fr.mcgalanes.rectus.feature.transactions.data.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.data.entity.TransactionsResponse
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TransactionRepositoryImplTest {

    private val service: TransactionApiService = mockk()
    private val repository = TransactionRepositoryImpl(service)

    @Test
    fun `getTransactions should return transactions when succeed`() = runTest {
        //GIVEN
        val transactions = mockk<List<TransactionEntity>>()
        val response = TransactionsResponse(transactions)
        coEvery { service.getTransactions() } returns Result.success(response)

        //WHEN
        val actual = repository.getTransactions()

        //THEN
        Assert.assertEquals(transactions, actual.getOrThrow())
    }

    @Test
    fun `getTransactions should return error when fail`() = runTest {
        //GIVEN
        val error = IOException()
        coEvery { service.getTransactions() } returns Result.failure(error)

        //WHEN
        val actual = repository.getTransactions()

        //THEN
        actual.onFailure { Assert.assertEquals(error, it) }
    }
}
