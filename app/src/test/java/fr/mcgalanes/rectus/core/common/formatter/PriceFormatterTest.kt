package fr.mcgalanes.rectus.core.common.formatter

import org.junit.Assert
import org.junit.Test


internal class PriceFormatterTest {

    @Test
    fun `should format price with plus symbol`() {
        Assert.assertEquals(
            "+25,89 €",
            25.89.toPriceString(showPlusSymbol = true)
        )
    }

    @Test
    fun `should format price without plus symbol`() {
        Assert.assertEquals(
            "25,89 €",
            25.89.toPriceString(showPlusSymbol = false)
        )
    }

    @Test
    fun `should format price with minus symbol`() {
        Assert.assertEquals(
            "-2 756,70 €",
            (-2756.7).toPriceString()
        )
    }
}