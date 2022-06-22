package fr.mcgalanes.rectus.feature.transactions.domain.mapper

import fr.mcgalanes.rectus.feature.transactions.domain.nextTransactionEntity
import java.time.ZonedDateTime
import kotlin.random.Random
import org.junit.Assert
import org.junit.Test


internal class TransactionMapperTest {

    @Test
    fun `should map to model`() {
        //GIVEN
        val entity = Random.nextTransactionEntity()

        //WHEN
        val model = entity.toModel()

        //THEN
        Assert.assertEquals(model.thumbUrl, entity.largeIcon?.url)
        Assert.assertEquals(model.title, entity.name)
        Assert.assertEquals(model.date, ZonedDateTime.parse(entity.date).toLocalDate())
        Assert.assertEquals(model.priceInDecimal, entity.amount!!.value)
    }
}