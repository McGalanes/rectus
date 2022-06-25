package fr.mcgalanes.rectus.core.common.formatter

import androidx.compose.ui.text.intl.Locale
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test


internal class DateFormatterTest {

    @Test
    fun `should format to short date`() {
        assertEquals(
            "15 Juin",
            LocalDate.of(2022, 6, 15).toShortDateString(locale = Locale("FR"))
        )
    }
}