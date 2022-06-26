package fr.mcgalanes.rectus.core.common.formatter

import androidx.compose.ui.text.intl.Locale
import java.time.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test


internal class DateFormatterTest {

    @Test
    fun `should format to short date according to locale`() {
        //GIVEN
        val date = LocalDateTime.of(2022, 6, 15, 12, 0)
        val locale = Locale("FR")

        //WHEN
        val actual = date.toShortDateString(locale)

        //THEN
        assertEquals("15 Juin", actual)
    }

    @Test
    fun `should format to full date with time according to locale`() {
        //GIVEN
        val date = LocalDateTime.of(2022, 6, 22, 8, 7)
        val locale = Locale("FR")

        //WHEN
        val actual = date.toFullDateWithTimeString(locale)

        //THEN
        assertEquals("Mercredi 22 Juin 2022 • 8:07", actual)
    }
}