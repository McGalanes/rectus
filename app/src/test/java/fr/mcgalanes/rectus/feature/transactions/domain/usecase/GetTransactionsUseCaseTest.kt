package fr.mcgalanes.rectus.feature.transactions.domain.usecase

import fr.mcgalanes.rectus.feature.transactions.domain.repository.TransactionsRepository
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
internal class GetTransactionsUseCaseTest {

    private val repository: TransactionsRepository = mockk()
    private val useCase = GetTransactionsUseCase(repository)

    @Test
    fun `should return transactions when succeed`() = runTest {
        //GIVEN
        val entities = Random.nextTransactionEntityList(size = 3)
        coEvery { repository.getTransactions() } returns Result.success(entities)

        //WHEN
        val actual = useCase()

        //THEN
        val expected = entities.map { it.toModel() }
        Assert.assertEquals(expected, actual.getOrThrow())
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