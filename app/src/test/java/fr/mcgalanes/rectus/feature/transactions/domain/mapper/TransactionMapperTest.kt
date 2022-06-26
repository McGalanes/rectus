package fr.mcgalanes.rectus.feature.transactions.domain.mapper

import fr.mcgalanes.rectus.feature.transactions.domain.nextTransactionEntity
import java.time.ZonedDateTime
import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Test


internal class TransactionMapperTest {

    @Test
    fun `should map to model`() {
        //GIVEN
        val entity = Random.nextTransactionEntity()

        //WHEN
        val model = entity.toModel()

        //THEN
        assertEquals(entity.largeIcon?.url, model.thumbUrl)
        assertEquals(entity.name, model.title)
        assertEquals(ZonedDateTime.parse(entity.date).toLocalDateTime(), model.date)
        assertEquals(entity.amount!!.value, model.priceInDecimal)
    }
}