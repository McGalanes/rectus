package fr.mcgalanes.rectus.feature.transactions.domain.usecase

import fr.mcgalanes.rectus.feature.transactions.domain.nextTransactionList
import fr.mcgalanes.rectus.feature.transactions.domain.repository.TransactionsRepository
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlin.random.Random
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class GetTransactionsUseCaseTest {

    private val repository: TransactionsRepository = mockk()
    private val useCase = GetTransactionsUseCase(repository)

    @Test
    fun `should return transactions when succeed`() = runTest {
        //GIVEN
        val transactions = Random.nextTransactionList(size = 3)
        coEvery { repository.getTransactions() } returns Result.success(transactions)

        //WHEN
        val actual = useCase()

        //THEN
        Assert.assertEquals(transactions, actual.getOrThrow())
    }

    @Test
    fun `should return error when fail`() = runTest {
        //GIVEN
        val error = IOException()
        coEvery { repository.getTransactions() } returns Result.failure(error)

        //WHEN
        val actual = useCase()

        //THEN
        Assert.assertEquals(error, actual.exceptionOrNull())
    }
}
