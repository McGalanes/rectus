package fr.mcgalanes.rectus.core.common.formatter

import androidx.compose.ui.text.intl.Locale as AndroidxLocale
import androidx.compose.ui.text.capitalize
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.toShortDateString(locale: AndroidxLocale = AndroidxLocale.current): String {
    val formattedMonth =
        month.getDisplayName(
            TextStyle.FULL,
            Locale(locale.language)
        ).lowercase().capitalize(locale)

    return "$dayOfMonth $formattedMonth"
}