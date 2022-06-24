package fr.mcgalanes.rectus.feature.transactions.data

import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.TransactionRemoteDataSource
import fr.mcgalanes.rectus.feature.transactions.data.repository.TransactionRepositoryImpl
import fr.mcgalanes.rectus.feature.transactions.domain.nextTransactionList
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlin.random.Random
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TransactionRepositoryImplTest {

    private val remoteDataSource: TransactionRemoteDataSource = mockk()
    private val repository = TransactionRepositoryImpl(remoteDataSource)

    @Test
    fun `getTransactions should return transactions when succeed`() = runTest {
        //GIVEN
        val transactions = Random.nextTransactionList(3)
        coEvery { remoteDataSource.getTransactions() } returns Result.success(transactions)

        //WHEN
        val actual = repository.getTransactions()

        //THEN
        Assert.assertEquals(transactions, actual.getOrThrow())
    }

    @Test
    fun `getTransactions should return error when fail`() = runTest {
        //GIVEN
        val error = IOException()
        coEvery { remoteDataSource.getTransactions() } returns Result.failure(error)

        //WHEN
        val actual = repository.getTransactions()

        //THEN
        Assert.assertEquals(error, actual.exceptionOrNull())
    }
}
