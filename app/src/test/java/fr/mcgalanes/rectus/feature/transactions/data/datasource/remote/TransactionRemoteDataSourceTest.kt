package fr.mcgalanes.rectus.feature.transactions.data.datasource.remote

import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity.TransactionsResponse
import fr.mcgalanes.rectus.feature.transactions.domain.mapper.toModel
import fr.mcgalanes.rectus.feature.transactions.domain.nextTransactionEntityList
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlin.random.Random
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TransactionRemoteDataSourceTest {

    private val service: TransactionApiService = mockk()
    private val dataSource = TransactionRemoteDataSource(service)

    @Test
    fun `should return transactions when succeed`() = runTest {
        //GIVEN
        val transactions = Random.nextTransactionEntityList(size = 3)
        val response = TransactionsResponse(transactions)
        coEvery { service.getTransactions() } returns Result.success(response)

        //WHEN
        val actual = dataSource.getTransactions()

        //THEN
        val expected = transactions.map { it.toModel() }
        Assert.assertEquals(expected, actual.getOrThrow())
    }

    @Test
    fun `should return error when fail`() = runTest {
        //GIVEN
        val error = IOException()
        coEvery { service.getTransactions() } returns Result.failure(error)

        //WHEN
        val actual = dataSource.getTransactions()

        //THEN
        Assert.assertEquals(error, actual.exceptionOrNull())
    }
}